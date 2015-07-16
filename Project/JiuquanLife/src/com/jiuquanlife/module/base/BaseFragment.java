package com.jiuquanlife.module.base;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment{
	
	private View content;
	
	protected void setContent(View content) {
		
		this.content = content;
	}
	
	protected View getContent(){
		return content;
	}
	
	public View findViewById(int id) {
		
		return content.findViewById(id);
	}
	
	protected void initClickListener(int resId, View.OnClickListener onClickListener) {
		
		if(content == null) {
			return;
		}
		
		content.findViewById(resId).setOnClickListener(onClickListener);
	}
	
	public void startActivity(Class<?> cls) {
		
		Intent intent = new Intent(getActivity(), cls);
		startActivity(intent);
	}
	
	
}
