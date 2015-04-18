package com.jiuquanlife.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityUtils {
	
	public static ArrayList<Integer> getIDsOfData(List<?> data){
		ArrayList<Integer> res  = null;
		if(data!=null && !data.isEmpty()) {
			Object obj = data.get(0);
			Field field = null;
			try {
				field = obj.getClass().getField("ID");
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(field!=null) {
				res = new ArrayList<Integer>();
				for(Object temp : data) {
					try {
						res.add((Integer)field.get(temp));
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return res;
	}
	

	public static ArrayList<String> getIDsStrOfData(List<?> data, String column){
		ArrayList<String> res  = null;
		if(data!=null && !data.isEmpty()) {
			Object obj = data.get(0);
			Field field = null;
			try {
				field = obj.getClass().getField(column);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(field!=null) {
				res = new ArrayList<String>();
				for(Object temp : data) {
					try {
						res.add(field.get(temp).toString());
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return res;
	}
	
	
	public static int getIdOfEntity(Object obj) {
	
		Field field = null;
		try {
			field = obj.getClass().getField("ID");
			return (Integer) field.get(obj);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getFieldOfEntity(Object obj, String column) {
		
		Field field = null;
		try {
			field = obj.getClass().getField(column);
			return field.get(obj).toString();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
