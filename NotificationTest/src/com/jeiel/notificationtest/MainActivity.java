public class MainActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button sendNoticeBtn=(Button)findViewById(R.id.send_notification_btn);
		sendNoticeBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				NotificationManager manager = (NotificationManager)getSystemServie(NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.ic_launcher, 
					"This is ticker text", System.currentTimeMillis());
				Intent intent = new Intent(this, NotificationActivity.class);
				PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 
					PendingIntent.FLAG_CANCEL_CURRENT);
				notification.setLastestInfo(this, "This, is content title", 
					"This is content text", pi);
				Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg"));
				notification.sound = soundUri;
				long[] vibrates = {0, 1000, 1000, 1000};
				notification.vibrate = vibrates;
				notification.ledARGB = Color.GREEN;
				notification.ledOnMS = 1000;
				notification.ledOffMs = 1000;
				notification.flags = Notification.FLAG_SHOW_LIGHTS;
				//notification.defaults = Notification.DEFAULT_ALL;
				manager.notify(1, notification);
			}
		});
	}
}