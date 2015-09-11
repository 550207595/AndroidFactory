public class LongRunningService extends Service{
	@Override
	public IBinder onBind(Intent intent){
		return null;
	}

	@Override
	public void onStartCommand(Intent intent, int flags, int startId){
		new Thread(new Runnable(){
			@Override
			public void run(){
				Log.d("LongRunningService", "executed at " + new Data().toString());
			}
		});
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int aMinute = 60 * 1000;
		long triggerAtTime = SystemClock.elapsedRealTime() + aMinute;
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent ,0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}
}