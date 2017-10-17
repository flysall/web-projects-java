package com.flysall.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyUtil {
	/**
	 * 对字符串进行md5加密
	 * @param plainText 未加密字符串
	 * @return 经过md5加密后的字符串
	 */
	public static String md5(String plainText){
		byte[] secretBytes = null;
		try{
			secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
		} catch (NoSuchAlgorithmException e){
			throw new RuntimeException("无法进行md5加密");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16); //十六进制数字
		//若生成的数字未满32位，前面补0
		for(int i = 0; i < 32 - md5code.length(); i++){
			md5code = "0" + md5code;
		}
		return md5code;
	}
	
	/**
	 * 对日期进行格式化输出
	 * @param date 日期对象
	 * @return 格式化后的日期
	 */
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 返回随机代码
	 * @return
	 */
	public static String createRandomCode(){
		return new Date().getTime() + UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 将Set<String>转换为List<Integer>
	 * @param set
	 * @return
	 */
	public static List<Integer> StringSetToIntegerList(Set<String> set){
		List<Integer> list = new ArrayList<>();
		for(String s : set){
			list.add(Integer.parseInt(s));
		}
		return list;
	}
}














