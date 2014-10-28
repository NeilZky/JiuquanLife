package com.jiuquanlife.cache;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiuquanlife.app.App;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.StringUtils;

public class SpCache {

	private static SharedPreferences sp;
	private static SharedPreferences.Editor editor;

	private static App app = App.getInstance();
	
	static {
		sp = app.getSharedPreferences("settings", Context.MODE_PRIVATE);
		editor = sp.edit();
		// 去掉了离线照片功能,一直是true
	}

	public static String getValue(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	public static void setValue(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	public static boolean getBoolean(String key) {
		return sp.getBoolean(key, false);
	}

	public static void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void putObject(String key, Object obj) {

		if (obj != null) {
			String jsonString = GsonUtils.toJson(obj);
			editor.putString(key, jsonString);
			editor.commit();
		}
	}
	
	
	public static void clearObject(String key){
		
		editor.putString(key, null);
		editor.commit();
	}

	public static <T> T getObject(String key, Class<T> cls) {
		
		String json = sp.getString(key, null);
		if (!StringUtils.isNullOrEmpty(json)) {
			return GsonUtils.toObj(json, cls);
		}
		return null;
	}
	
	public static <T> List<T> getListObj(String key, Type type){
		
		String json = sp.getString(key, null);
		if (!StringUtils.isNullOrEmpty(json)) {
			return GsonUtils.getListObj(json, type);
		}
		return null;
	}
	

	
	
	
}
