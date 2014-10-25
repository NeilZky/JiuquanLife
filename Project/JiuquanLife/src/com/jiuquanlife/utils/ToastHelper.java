package com.jiuquanlife.utils;

import android.widget.Toast;

import com.jiuquanlife.app.App;

public class ToastHelper {

	private static App app = App.getInstance();
	
	public static void showL(String text) {
		
		Toast.makeText(app, text, Toast.LENGTH_LONG).show();
	}
	
	public static void showS(String text) {
		
		Toast.makeText(app, text, Toast.LENGTH_SHORT).show();
	}

	public static void showL(int text) {
	
		Toast.makeText(app, text, Toast.LENGTH_LONG).show();
	}

	public static void showS(int text) {
	
		Toast.makeText(app, text, Toast.LENGTH_SHORT).show();
	}
	
}
