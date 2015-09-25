package com.jeiel.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button sendNoticeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendNoticeBtn = (Button)findViewById(R.id.send_notification_btn);
        sendNoticeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.ic_launcher,
                "This is ticker text", System.currentTimeMillis());
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLastestEventInfo(this, "This, is content title",
                "This is content text", pi);
        Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg"));
        notification.sound = soundUri;
        long[] vibrates = {0, 1000, 1000, 1000};
        notification.vibrate = vibrates;
        notification.ledARGB = Color.GREEN;
        notification.ledOnMS = 1000;
        notification.ledOffMS = 1000;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        //notification.defaults = Notification.DEFAULT_ALL;
        manager.notify(1, notification);*/

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setTicker("This is ticker text");
        Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Cairo.ogg"));
        builder.setSound(soundUri);
        long[] vibrates = {0, 1000, 1000, 1000};
        builder.setVibrate(vibrates);
        builder.setContentTitle("This, is content title");
        builder.setContentText("This is content text");
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pi);
        Notification notification = builder.build();
        manager.notify(1, notification);

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
