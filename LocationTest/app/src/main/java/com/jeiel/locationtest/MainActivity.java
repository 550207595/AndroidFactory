package com.jeiel.locationtest;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    private TextView locationText;
    private LocationManager manager;
    private String provider;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
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

        if (providers.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = manager.getLastKnownLocation(provider);
        if(location != null){
            showLocation(location);
        }
        manager.requestLocationUpdates(provider, 1000, 1, locationListener);
    }

    private void showLocation(Location location){
        StringBuilder locationStr = new StringBuilder();
        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            locationStr.append("GPS_PROVIDER:\n");
            locationStr.append("Latitude: " + location.getLatitude() + "\n");
            locationStr.append("Longitude: " + location.getLongitude() + "\n");
        }else if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
            locationStr.append("NETWORK_PROVIDER:\n");
            locationStr.append("Latitude: " + location.getLatitude() + "\n");
            locationStr.append("Longitude: " + location.getLongitude() + "\n");
        }
        locationStr.append("Time: " + new Date());
        locationText.setText(locationStr);
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
