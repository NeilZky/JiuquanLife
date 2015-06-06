package com.jiuquanlife.utils;

import android.widget.TextView;

public class TextViewUtils {
	
	public static void setText(TextView tv, String text) {
		
		if(!StringUtils.isNullOrEmpty(text)) {
			tv.setText(text);
		}
	}
}
