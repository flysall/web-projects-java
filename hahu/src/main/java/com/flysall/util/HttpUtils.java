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

/**
 * 用于处理http请求的工具类
 */
public class HttpUtils {
	/**
	 * 发送http请求，返回获得的网页源码
	 * @param url 请求的网址
	 * @param map
	 * @param encoding 编码
	 * @return 网页源码
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String send(String url, Map<String, String> map, String encoding) throws ParseException, IOException{
		String body = "";
		
		//创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建Post请求对象
		HttpPost httpPost = new HttpPost(url);
		
		//配置参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(map != null){
			for(Entry<String, String> entry : map.entrySet()){
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		//设置请求参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
		
		System.out.println("请求地址: " + url);
		System.out.println("请求参数: " + nvps.toString());
		
		//设置header信息
		//设置请求报文头
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		
		//执行请求，拿到结果（同步阻塞)
		CloseableHttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if(entity != null){
			//按指定编码将结果转换为String对象
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		System.out.println("返回结果" + body);
		return body;
	}
	
	/**
	 * 发送Get请求，返回网页源码
	 * @param url
	 * @return 网页源码
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		System.out.println(response1.getStatusLine());
		HttpEntity entity = response1.getEntity();
		
		String body = "";
		if(entity != null){
			body = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		response1.close();
		System.out.println("返回结果: " ＋ body);
		return body;
	}
}



































