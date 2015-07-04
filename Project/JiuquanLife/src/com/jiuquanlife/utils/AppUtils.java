package com.jiuquanlife.utils;

public class AppUtils {

	public static String getAppHash() {
		return Md5Utils.md5(
				new StringBuilder(String.valueOf(System
						.currentTimeMillis())).toString().substring(0,
						5)
						+ "appbyme_key").substring(8, 16).toLowerCase();

	}
}
