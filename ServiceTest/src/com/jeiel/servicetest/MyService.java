public class MyService extends Service{
	private DownloadBinder mBinder = new DownloadBinder();

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

		Notification notification = new Notification(R.drawable.ic_launcher, 
			"Notification comes", System.currentTimeMillis());
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 
			PendingIntent.FLAG_CANCEL_CURRENT);
		notification.setLatestEventInfo(this, "This is title", "This is content", pi);
		startForeground(1, notification);

		Log.d("MyService", "onCreate executed");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyService", "onStartCommand executed");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d("Myservice", "onDestroy executed");
	}
}