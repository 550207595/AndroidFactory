public class AlarmReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent){
		Intent intent = new Intent(context, LongRunningService.class);
		startService(intent);
	}
}