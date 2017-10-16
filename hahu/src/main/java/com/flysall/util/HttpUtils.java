package com.flysall.util;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	public static String send(String url, Map<String, String> map, String encoding) throws ParseExcepton, IOExcepton{
		String body = "";
		
		//创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建Post请求对象
		HttpPost httpPost = new HttpPost(url);
		
		//配置参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(map != null){
			for(Entry<String, String> entry : map.entrySet()){
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue());
			}
		}
		//设置请求参数到请求对象中
		
	}
}

















