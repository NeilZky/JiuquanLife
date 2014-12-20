package com.jiuquanlife.module.base;

import com.jiuquanlife.utils.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class BaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	protected void setText(TextView tv, String value) {
		
		if(!StringUtils.isNullOrEmpty(value)) {
			tv.setText(value);
		}
	}
	
}
