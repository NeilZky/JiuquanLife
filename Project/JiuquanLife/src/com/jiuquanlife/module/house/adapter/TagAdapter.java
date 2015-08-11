package com.jiuquanlife.module.house.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;

public class TagAdapter extends BaseListAdapter<String>{

	public TagAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_tag, null);
		}
		TextView tv = (TextView) convertView;
		tv.setText(getItem(position));
		return convertView;
	}
	
}
