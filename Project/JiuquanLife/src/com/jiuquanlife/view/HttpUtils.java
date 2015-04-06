package com.jiuquanlife.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUtils {
	
	public static Bitmap getHttpBitmap(String url) {

		Log.d("csb", "start getHttpBitmap");
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url.replace(" ", "%20"));
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			conn.setConnectTimeout(0);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(6000);
			conn.setReadTimeout(2000);
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.setUseCaches(true);
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return bitmap;
	}
	
	/**
	 * return true if download success
	 * @param url
	 * @param fileName
	 * @param storeFolder
	 * @return
	 */
	public static boolean downloadFile(String url, String filePath){
		
		File distFile = new File(filePath);
		if(distFile.exists()){
			return true;
		} else {
			File parentFolder = distFile.getParentFile();
			if(!parentFolder.exists()){
				parentFolder.mkdirs();
			}
		}
		
		URL myFileURL;
		try {
			int lastIndex = url.lastIndexOf("/");
			String urlPath = url.substring(0, lastIndex+1);
			String fileName = url.substring(lastIndex+1);
			String encodeFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
			myFileURL = new URL(urlPath + encodeFileName);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			conn.setConnectTimeout(0);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(6000);
			conn.setReadTimeout(2000);
			// 连接设置获得数据�?
			conn.setDoInput(true);
			conn.setDoOutput(false);
			// 不使用缓�?
			conn.setUseCaches(true);
			InputStream is = conn.getInputStream();
			byte[] buffer = new  byte[1024];
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			// 解析得到图片
			int index = 0;
			while((index = is.read(buffer))!=-1){
				fos.write(buffer, 0 ,index);
			}
			is.close();
			fos.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
		
		return true;
	}
	
	public static String getJson(String urlStr){
		
		URL url = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(20000); 
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = null;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
