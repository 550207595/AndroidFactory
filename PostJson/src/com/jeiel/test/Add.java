package com.jeiel.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class Add {
	private static String postUrl = "http://myoffer.cn/external/api/courses";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		for(int i=1;i<=10;i++){
			System.out.println("add "+i);
			add(postUrl);
		}
	   
	}
	
	public static HttpURLConnection getConnection(String postUrl) throws IOException{
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
	    return connection;
	}
	
	public static void add(String postUrl) throws IOException{
	    
		HttpURLConnection connection=getConnection(postUrl);
		DataOutputStream out= new DataOutputStream(connection.getOutputStream());
		
	    //固定值
	    JSONObject entry=new JSONObject();
	    entry.put("target", "course");
	    entry.put("action", "add");
	    
	    //自定义值
	    
	    JSONObject course=new JSONObject();
	    course.put("school", "School");
	    course.put("level", "Level");
	    course.put("title", "Title");
	    course.put("type", "Type");
	    course.put("application", 1);
	    course.put("tuition", 1);
	    course.put("academic", 1);
	    course.put("ielts_avg", 1);
	    course.put("ielts_low", "");
	    course.put("ielts_low_l", "");
	    course.put("ielts_low_s", 1);
	    course.put("ielts_low_r", 1);
	    course.put("ielts_low_w", 1);
	    
	    JSONObject structureItem;
	    List<JSONObject> structureList=new ArrayList<JSONObject>();
	    for(int i=0;i<4;i++){//i的长度为年数
	    	structureItem=new JSONObject();
	    	structureItem.put("category", ""+i);
	    	structureItem.put("summary", ""+i+""+i+""+i+""+i);
	    	structureList.add(structureItem);
	    }
	    if(structureList.size()>0)
	    	course.put("structure", structureList);
	    
	    course.put("length", 36);
	    course.put("month", 9);
	    
	    JSONObject scholarshipItem;
	    List<JSONObject> scholarshipList=new ArrayList<JSONObject>();
	   	for(int i=0;i<3;i++){//i个长度为奖学金个数
	   		scholarshipItem=new JSONObject();
	   		scholarshipItem.put("name", ""+i);
	   		scholarshipItem.put("value", ""+i+""+i+""+i);
	   		scholarshipList.add(scholarshipItem);
	   	}
	   	if(scholarshipList.size()>0)
	   		course.put("scholarship", scholarshipList);
	   	
	   	
	   	JSONObject value=new JSONObject();
	    value.put("university", "saos");
	    value.put("course", course);
	   	entry.put("value", value);
	    
	    /*{"target":"course","action":"add",
	    	"value":{"university":"saos",
	    		"course":{"school":"School","level":"Level","title":"Title","type":"Type","application":1,"tuition":1,"academic":"1","ielts_avg":1,"ielts_low":1,"ielts_low_l":1,"ielts_low_s":1,"ielts_low_r":1,"ielts_low_w":1,
	    			"structure":[{"category":"1","summary":"1111"},{"category":"2","summary":"2222"},{"category":"3","summary":"3333"},{"category":"4","summary":"4444"}],
	    			"length":36,"month":9,
	    				"scholarship":[{"name":"1","value":"111"},{"name":"2","value":"222"}]}}}*/
	    
	    
	    //System.out.println(entry.toString());
	    out.writeBytes(entry.toString());
	    out.flush();
	    
	    //读取响应

	    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String lines;
	    StringBuffer sb = new StringBuffer("");
	    while ((lines = reader.readLine()) != null) {
	    	lines = new String(lines.getBytes(), "utf-8");
	    	sb.append(lines);
	    }
	    
	    
	    System.out.println(sb);
	    
	    out.close();
	    connection.disconnect();
	    reader.close();
	}
	

}
