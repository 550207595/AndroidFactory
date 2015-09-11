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
			/*HttpUtil.sendHttpRequest("http://www.baidu.com", new HttpCallbackListener(){
				@Override
				public void onFinish(String response){
					Message msg = new Message();
					msg.what = SHOW_RESPONSE;
					msg.obj = response;
					handler.sendMessage(msg);
				}
				@Override
				public void onError(Exception e){
					e.printStackTrace();
				}
			});*/
			break;
		case R.id.send_request_2:
			/*sendRequestWithHttpUrlConnection(new Url("http://www.baidu.com"));*/
			/*sendRequestWithHttpClient(new Url("http://127.0.0.2:8088/get_data.xml"));*/
			sendRequestWithHttpClient(new Url("http://127.0.0.2:8088/get_data.json"));
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
				
				/*try{
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
				}*/
				/*try{
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if(httpResponse.getStatusLine().getStatusCode() == 200){
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtil.toString(entity, "utf-8");
						
						parseXMLWithPull(response);
						parseXMLWithSAX(response);
					}
				}catch(Exception e){
					e.printStackTrace();
				}*/
				try{
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if(httpResponse.getStatusLine().getStatusCode() == 200){
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtil.toString(entity, "utf-8");
						
						parseJSONWithJSONObject(response);
						parseJSONWithGSON(response);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void parseXMLWithPull(String xmlData){
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlData));
			int eventType = xmlPullparser.getEventType();
			String id = "";
			String name = "";
			String version = "";
			while(eventType != XmlPullParser.END_DOCUMENT){
				String nodeName = xmlPullparser.getName();
				switch(eventType){
				case XmlPullParser.START_TAG:
					if("id".equals(nodeName)){
						id = xmlPullParser.nextText();
					}else if("name".equals(nodeName)){
						name = xmlPullParser.nextText();
					}else if("version".equals(nodeName)){
						version = xmlPullParser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					if("app".equlas(nodeName)){
						Log.d("MainActivity", "id is " + id);
						Log.d("MainActivity", "name is " + name);
						Log.d("MainActivity", "version is " + version);
					}
					break;
				default:
					break;
				}
				eventType = xmlPullParser.next();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private void parseXMLWithSAX(String xmlData){
		try{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader xmlReader = factory.newSAXParser().getReader();
			ContentHandler handler = new ContentHandler();
			xmlReader.setContentHandler(handler);
			xmlReader.parse(new InputSource(new StringReader(xmlData)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void parseJSONWithJSONObject(String jsonData){
		try{
			JsonArray jsonArray = new JsonArray(jsonData);
			for(int i = 0; i < jsonArray.length; i++){
				JSONObject jsonObject = jsonArray.get(i);
				String id = jsonObject.getString("id");
				String name = jsonObject.getString("name");
				String version = jsonObject.getString("version");

				Log.d("MainActivity", "id is " + id);
				Log.d("MainActivity", "name is " + name);
				Log.d("MainActivity", "version is " + version);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void parseJSONWithGSON(String jsonData){
		try{
			Gson gson = new Gson();
			List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
			for(App app : appList){
				Log.d("MainActivity", "id is " + app.getId());
				Log.d("MainActivity", "name is " + app.getName());
				Log.d("MainActivity", "version is " + app.getVersion());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}