package com.jeiel.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Delete {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String ADD_URL = "http://myoffer.cn/external/api/courses";
		URL url = new URL(ADD_URL);

	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setDoOutput(true);
	    connection.setDoInput(true);
	    connection.setRequestMethod("POST");
	    connection.setUseCaches(false);
	    connection.setInstanceFollowRedirects(true);
	    connection.setRequestProperty("Accept", "application/json, text/plain, */*");
	    connection.setRequestProperty("Content-Type","application/json;charset=utf-8");
	    connection.setRequestProperty("Referer", "http://myoffer.cn/external/course");
	    connection.setRequestProperty("Cookie", "CNZZDATA1256122972=436580706-1440482499-http%253A%252F%252Fmyoffer.cn%252F%7C1441087693; connect.sid=s%3AkmPA2lJJjDsGR4Ag60QDFLl21VbxrP7_.oEtXldNCyVcbQsodvVe%2FsCXE7X%2BEJ7zxr3pxcZgmFlQ");
	    connection.setRequestProperty("Connection", "keep-alive");
	    connection.setRequestProperty("Pragma", "no-cache");
	    connection.setRequestProperty("Cache-Control", "no-cache");
	    
	    connection.connect();

	    
	    //POST请求
	    DataOutputStream out = new DataOutputStream(connection.getOutputStream());

	    /*JSONObject bj = new JSONObject();
	    obj.element("app_name", "asdf");
	    obj.element("app_ip", "10.21.243.234");
	    obj.element("app_port", 8080);
	    obj.element("app_type", "001");
	    obj.element("app_area", "asd");*/

    	out.writeBytes("{\"target\":\"course\",\"action\":\"remove\",\"value\":{\"university\":\"saos\",\"id\":40}}");
	    out.flush();

	    
	    out.close();

	    //读取响应

	    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String lines;
	    StringBuffer sb = new StringBuffer("");
	    while ((lines = reader.readLine()) != null) {
	    	lines = new String(lines.getBytes(), "utf-8");
	    	sb.append(lines);
	    }

	    System.out.println(sb);
	    reader.close();
	    // 断开连接
	    connection.disconnect();
	}

	
}
