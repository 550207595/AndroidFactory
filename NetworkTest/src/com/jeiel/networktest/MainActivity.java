public class MainActivity extends Activity implements OnClickListener{
	private static final String SHOW_RESPONSE = 0;
	private Button sendRequest1;
	private Button sendRequest2;
	private TextView responseText;
	private Handler handler = new Handler(){
		@Override
		public void onHandlMessage(Message msg){
			switch(msg.what){
			case SHOW_RESPONSE:
				responseText.setText((String)msg.obj);
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
		sendRequest1 = (Button) findViewById(R.id.send_request_1);
		sendRequest2 = (Button) findViewById(R.id.send_request_2);
		sendRequest1.setOnClickListener(this);
		sendRequest2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v){
		switch(v.getId()){
		case R.id.send_request_1:
			sendRequestWithHttpUrlConnection(new Url("http://www.baidu.com"));
			break;
		case R.id.send_request_2:
			sendRequestWithHttpClient(new Url("http://www.baidu.com"));
			break;
		default:
			break;
		}
	}

	private void sendRequestWithHttpUrlConnection(Url url){
		new Thread(new Runnable(){
			@Override
			public void run(){
				HttpUrlConnection connection = null;
				try{
					connection = url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectionTimeout(5000);
					connection.setReadTimeout(5000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(in);
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine()) != null){
						response.append(line);
					}
					Message msg = new Message();
					msg.what = SHOW_RESPONSE;
					msg.obj = response;
					handler.sendMessage(msg);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(connection != null){
						connection.disconnect();
					}
				}
				
			}
		}).start();
	}

	private void sendRequestWithHttpClient(Url url){
		new Thread(new Runnable(){
			@Override
			public void run(){
				
				try{
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if(httpResponse.getStatusLine().getStatusCode() == 200){
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtil.toString(entity, "utf-8");
						Message msg = new Message();
						msg.what = SHOW_RESPONSE;
						msg.obj = response;
						handler.sendMessage(msg);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}).start();
	}
}