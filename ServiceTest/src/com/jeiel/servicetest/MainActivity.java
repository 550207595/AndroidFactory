public class MainActivity extends Activity implements OnClickListener{
	private Button startService;
	private Button stopService;
	private Button bindService;
	private Button unbindService;
	private Button startIntentService;
	private MyService.DownloadBinder downloadBinder;
	private ServiceConnection connection = new ServiceConnection(){
		@Override
		public void onServiceDisconnected(ComponentName name){

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service){
			downloadBinder = (MyService.DownloadBinder) service;
			downloadBinder.startDownload();
			downloadBinder.getProgress();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_main);
		startService = (Button) findViewById(R.id.start_service);
		stopService = (Button) findViewById(R.id.stop_service);
		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
		bindService = (Button) findViewById(R.id.bind_service);
		unbindService = (Button) findViewById(R.id.unbind_service);
		bindService.setOnClickListener(this);
		unbindService.setOnClickListener(this);
		startIntentService = (Button) findViewById(R.id.start_intent_service);
		startIntentService.setOnClickListener(this);
	}

	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.start_service:
			Intent intent = new Intent(this, MyService.class);
			startService(intent);
			break;
		case R.id.stop_service:
			Intent intent = new Intent(this, MyService.class);
			stopService(intent);
			break;
		case R.id.bind_service:
			Intent intent = new Intent(this, MyService.class);
			bindService(intent, connection, BIND_AUTO_CREATE);
			break;
		case R.id.unbind_service:
			unbindService(connection);
			break;
		case R.id.start_intent_service:
			Log.d("MainActivity", "Thread id is " + Thread.currentThread().getId());
			Intent intent = new Intent(this, MyIntentService.class);
			startService(intent);
			break;
		default:
			break;
		}
	}
}