package com.jeiel.locationtest;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    private TextView locationText;
    private LocationManager manager;
    private final int SHOW_LOCATION = 0;
    private Handler handler = new Handler(){
      public void handleMessage(Message msg){
          switch (msg.what){
              case SHOW_LOCATION:
                  locationText.setText((String)msg.obj);
                  break;
              default:
                  break;
          }
      }
    };
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            List<Location> locations = new ArrayList<Location>();
            locations.add(location);
            showLocation(locations);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {        }

        @Override
        public void onProviderDisabled(String provider) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationText = (TextView) findViewById(R.id.location_text);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = manager.getProviders(true);
        List<Location> locations = new ArrayList<Location>();
        providers.remove(manager.PASSIVE_PROVIDER);
        if(providers.size() == 0){
            Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
        if (providers.contains(LocationManager.GPS_PROVIDER)){
            locations.add(manager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
        if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            locations.add(manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }
        if(locations.size()>0){
            locationText.setText("Locating...");
            showLocation(locations);
        }
        for(String provider : providers){
            manager.requestLocationUpdates(provider, 1000, 1, locationListener);
        }
    }

    private void showLocation(final List<Location> locations){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder locationStr = new StringBuilder();
                for(Location location : locations){
                    if(location != null){
                        HttpClient client = new DefaultHttpClient();

                        StringBuilder url = new StringBuilder();
                        locationStr.append(location.getProvider() + ":\n");
                        locationStr.append("Latitude: " + location.getLatitude() + "\n");
                        locationStr.append("Longitude: " + location.getLongitude() + "\n");
                        url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
                        url.append(location.getLatitude() + "," + location.getLongitude() + "&sensor=false");
                        HttpGet httpGet = new HttpGet(url.toString());
                        httpGet.addHeader("Accept-Language", "zh-CN");
                        try{
                            HttpResponse httpResponse = client.execute(httpGet);
                            if(httpResponse.getStatusLine().getStatusCode() == 200){
                                HttpEntity entity = httpResponse.getEntity();
                                String response = EntityUtils.toString(entity, "utf-8");
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray resultArray = jsonObject.getJSONArray("results");
                                if(resultArray.length()>0){
                                    JSONObject subObject = resultArray.getJSONObject(0);
                                    String address = subObject.getString("formatted_address");
                                    locationStr.append("Address: "+ address + "\n");
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        locationStr.append("Time: " + new Date() + "\n");
                    }
                }
                Message message = new Message();
                message.what = SHOW_LOCATION;
                message.obj = locationStr.toString();
                handler.sendMessage(message);
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(manager != null){
            manager.removeUpdates(locationListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
