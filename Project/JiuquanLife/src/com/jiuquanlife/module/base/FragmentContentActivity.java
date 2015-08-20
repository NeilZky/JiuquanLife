package com.jiuquanlife.module.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jiuquanlife.R;

public class FragmentContentActivity extends BaseActivity {

	public static final String EXTRA_FRAGMENT_NAME = "EXTRA_FRAGMENT_NAME";
	private Fragment fragment; 
	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		init();
	}

	private void init() {

		setContentView(R.layout.activity_fragment_content);
		String fragmentName =  getIntent().getStringExtra(EXTRA_FRAGMENT_NAME);
		Bundle data = getIntent().getExtras();
		fragment = Fragment.instantiate(getActivity(), fragmentName, data);
		if(fragment!=null) {
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.fl_content, fragment).commit();
		}
	}

	
	
}
