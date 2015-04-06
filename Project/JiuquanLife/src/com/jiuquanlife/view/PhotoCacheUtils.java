package com.jiuquanlife.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

import com.jiuquanlife.utils.BitmapUtils;
import com.jiuquanlife.utils.StringUtils;

public class PhotoCacheUtils {

	private static final String SDCARD = Environment
			.getExternalStorageDirectory().getPath();
	public static final String PHOTO_CACHE_FOLDER = SDCARD + "/checkin/photoCache/";

//	部分手机软引用支持不�?TODO 改成lrucache或�?用现成的volley框架
//	private static HashMap<String, Reference<Bitmap>> cache = new HashMap<String, Reference<Bitmap>>();

	public static synchronized Bitmap getBitmap(String url, int reqWidth, int reqHeight) {
		if(StringUtils.isNullOrEmpty(url)){
			return null;
		}
		if(reqWidth == 0) {
			reqWidth = 50;
		}
		if(reqHeight == 0) {
			reqHeight = 50;
		}
		return getBitmapFromSDCard(url, reqWidth, reqHeight);
//     不�?用软引用缓存
//		Bitmap res = null;
//		Reference<Bitmap> sr = cache.get(url);
//		if (sr != null && sr.get() != null) {
//			res = sr.get();
//		} else {
//			res = getBitmapFromSDCard(url, reqWidth, reqHeight);
//			sr = new SoftReference<Bitmap>(res);
//			cache.put(url, sr);
//		}
//		return res;
	}

	private static Bitmap getBitmapFromSDCard(String url, int reqWidth, int reqHeight) {

		String fileName = generateFilePathFromUrl(url);
		String path = PHOTO_CACHE_FOLDER + fileName;
		File file = new File(path);
		Bitmap bitmap = null;
		if (file.exists()) {
			bitmap = BitmapUtils.decodeSampledBitmapFromSDCard(path, reqWidth, reqHeight);
		} 
		if(bitmap == null) {
			file.delete();
			bitmap = getBitmapFromRemote(url, reqWidth, reqHeight);
		}
		return bitmap;
	}

	private static Bitmap getBitmapFromRemote(String url, int reqWidth, int reqHeight) {
		
		String fileName = generateFilePathFromUrl(url);
		String path = PHOTO_CACHE_FOLDER + fileName;
		boolean success = HttpUtils.downloadFile(url, path);
		if(success){
			return BitmapUtils.decodeSampledBitmapFromSDCard(path, reqWidth, reqHeight);
		} else {
			return null;
		}
	}

	private static String generateFilePathFromUrl(String url) {

		String[] names = url.split("/");
		int length = names.length;
		String fileName = names[length - 1];
		String folderName = names[length - 3];
		return folderName + "_" + fileName;
	}

	private static void saveBitmapToSDCard(Bitmap bitmap, String url) {

		String fileName = generateFilePathFromUrl(url);

		File path = new File(PHOTO_CACHE_FOLDER);
		if (!path.exists()) {
			path.mkdirs();
		}
		File file = new File(PHOTO_CACHE_FOLDER, fileName);
		FileOutputStream bitmapWtriter;
		try {
			bitmapWtriter = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapWtriter)) {
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearPhotoCache(){
		
		File file = new File(PHOTO_CACHE_FOLDER);
		File[] fileChildren = file.listFiles();
		if(fileChildren!=null){
			for(File temp : fileChildren){
				temp.delete();
			}
		}
	}
	
}
