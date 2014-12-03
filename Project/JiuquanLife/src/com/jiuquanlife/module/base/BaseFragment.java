package com.jiuquanlife.module.base;

import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment{
	
	private View content;
	
	public void setContent(View content) {
		
		this.content = content;
	}
	
	public View findViewById(int id) {
		
		return content.findViewById(id);
	}
	
}
