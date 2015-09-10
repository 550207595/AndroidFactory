public class MainActivity extends Activity implements OnClickListener{
	private static final String UPDATE_TEXT = 1;
	private Button changeText;
	private TextView text;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_TEXT:
				text.setText("Nice to meet you");
				break;
			default:
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		changeText = (Button) findViewById(R.id.change_text);
		text = (TextView) findViewById(R.id.text);
		changeText.setOnClickListener(this);
	}

	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.change_text:
			new Thread(new Runnable(){
				@Override
				public void run(){
					Message message = new Message();
					message.what = UPDATE_TEXT;
					handler.sendMessage(message);
				}
			}).start();
			break;
		default:
			break;
		}
	}
}