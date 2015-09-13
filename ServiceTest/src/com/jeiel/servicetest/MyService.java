package com.jeiel.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.AlarmClock;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class MyService extends Service{
	private DownloadBinder mBinder = new DownloadBinder();
	private static boolean THREAD_STOP = false;

	class DownloadBinder extends Binder{
		public void startDownload(){
			Log.d("MyService", "startDownload executed");
		}

		public int getProgress(){
			Log.d("MyService", "getProgress executed");
			return 0;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Notification.Builder builder = new Notification.Builder(this);
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setContentTitle("This is title");
		builder.setContentText("This is content");

		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		builder.setContentIntent(pi);

		Notification notification = builder.build();
		startForeground(1, notification);

		Log.d("MyService", "onCreate executed");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyService", "onStartCommand executed");
		new Thread(new Runnable(){
			@Override
			public void run(){
				while(!THREAD_STOP) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Log.d("MyService", new Date().toString());
				}
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		THREAD_STOP = true;
		Log.d("Myservice", "onDestroy executed");
	}
}