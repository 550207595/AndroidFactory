package com.jeiel.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

public class PostJson {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String postUrl = "http://myoffer.cn/external/api/courses";
		
		for(int i=0;i<10;i++)post(postUrl);
	   
	}
	
	public static void post(String postUrl) throws IOException{
		
		URL url = new URL(postUrl);

	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setDoOutput(true);
	    connection.setDoInput(true);
	    connection.setRequestMethod("POST");
	    connection.setUseCaches(false);
	    connection.setInstanceFollowRedirects(true);
	    //connection.setRequestProperty("Host", "myoffer.cn");
	    //connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:40.0) Gecko/20100101 Firefox/40.0");
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

	    JSONObject obj = new JSONObject();
	    JSONObject value=new JSONObject();
	    JSONObject course=new JSONObject();
	    
	    course.put("school", "java");
	    
	    value.put("university", "saos");
	    value.put("course", course);
	    
	    obj.put("target", "course");
	    obj.put("action", "add");
	    obj.put("value", value);
	    
	    String addStr="{\"target\":\"course\",\"action\":\"add\",\"value\":{\"university\":\"saos\",\"course\":{\"school\":\"java\"}}}";
	    System.out.println(obj.toString());
	    out.writeBytes(obj.toString());
	    out.flush();
	    
	    //out.close();

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
