package com.flysall.util;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Service
public class QiniuyunUtil {
	//账号的ACESS_KEY和SECTET_KEY
	private static String ACCESS_KEY = MyConstant.QINIU_ACCESS_KEY;
	private static String SECRET_KEY = MyConstant.QINIU_SECRET_KEY;
	//要上传的空间
	private static String BUCKET_NAME = MyConstant.QINIU_BUCKET_NAME;
	//密钥配置
	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	//创建上传对象
	private static UploadManager uploadManager = new UploadManager();
	
	/**
	 * 使用默认策略，只需设置上传的空间名
	 * @return
	 */
	public static String getUpToken(){
		return auth.uploadToken(BUCKET_NAME);
	}
	
	/**
	 * 将本地数据上传到云端
	 * @param localData 本地数据
	 * @param remoteFileName 云端文件名
	 * @throws IOException
	 */
	public static void upload(byte[] localData, String remoteFileName) throws IOException {
		Response res = uploadManager.put(localData, remoteFileName, getUpToken());
		System.out.println(res.bodyString());
	}
}