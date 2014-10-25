package com.jiuquanlife.utils;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonUtils {

	private static Gson gson = new Gson();
	
	public static String toJson(Object obj) {
		
		return gson.toJson(obj);
	}
	
	public static <T> T  toObj(String json, Class<T> cls) {
		
		return gson.fromJson(json, cls);
	}

	public static <T> List<T> getListObj(String json, Type type) {
		
		try {
			return gson.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
}
