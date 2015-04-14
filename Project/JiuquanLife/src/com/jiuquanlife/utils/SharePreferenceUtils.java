package com.jiuquanlife.utils;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiuquanlife.app.App;

public class SharePreferenceUtils {

	private static SharedPreferences sp;
	private static SharedPreferences.Editor editor;
	private static App app = App.getInstance();
	
	public static final String USER = "USER";
	
	static {
		sp = app.getSharedPreferences("settings", Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public SharePreferenceUtils(Context context) {
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
	
	public static long getLong(String key) {
		
		return sp.getLong(key, 0L);
	}
	
	public static int getInt(String key) {
		
		return sp.getInt(key, 0);
	}
	
	public static void putInt(String key, int value) {
		
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void putLong(String key, long value) {
		
		editor.putLong(key, value);
		editor.commit();
	}

	public static void putObject(String key, Object obj) {

		if (obj != null) {
			String jsonString = GsonUtils.toJson(obj);
			editor.putString(key, jsonString);
			editor.commit();
		} else {
			clearObject(key);
		}
	}
	
	
	public static void clearObject(String key){
		
		editor.putString(key, null);
		editor.commit();
	}
	
	public static void clearAll() {
		
		editor.clear();
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
