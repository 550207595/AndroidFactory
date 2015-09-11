public class HttpUtil{
	public static void sendHttpRequest(final String address, final 
		HttpCallbackListener listener){
		new Thread(new Runnable(){
			@Override
			public void run(){
				HttpUrlConnection connection = null;
				try{
					Url url = new Url(address);
					connection = url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					connection.setReadTimeout(5000);
					connection.setDoInput(true);
					connection.setDoOutput(true);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					String line;
					StringBuilder response = new StringBuilder();
					while((line = reader.nextLine()) != null){
						response.append(line);
					}
					if(listener != null){
						listener.onFinish(response.toString());
					}
				}catch(Exception e){
					if(listener != null){
						listener.onError(e);
					}
				}finally{
					if(connection != null){
						connection.disconnect();
					}
				}
			}
		}).start();
		
	}
}