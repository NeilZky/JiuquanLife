package com.jiuquanlife.utils;

public class StringUtils {
	
	public static boolean isNullOrEmpty(String input) {
	
		if(input == null || input.isEmpty()) {
			return true;
		}
		return false;
	}
	
}
