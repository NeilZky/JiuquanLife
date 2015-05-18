package com.jiuquanlife.module.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.jiuquanlife.utils.StringUtils;

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
	
	protected void setText(int res, String value) {
		
		TextView tv = (TextView) findViewById(res);
		if(!StringUtils.isNullOrEmpty(value)) {
			tv.setText(value);
		}
	}
	
	protected Activity getActivity() {
		
		return this;
	}
	
}
