package com.jiuquanlife.module.forum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.vo.forum.usercenter.UserData;

public class ProfileAdapter extends BaseListAdapter<UserData>{

	public ProfileAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder = null;
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_profile, null);
			holder = new Holder();
			holder.tv_label_adapter_profile = (TextView) convertView.findViewById(R.id.tv_label_adapter_profile);
			holder.tv_val_adapter_profile = (TextView) convertView.findViewById(R.id.tv_val_adapter_profile);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		UserData item = getItem(position);
		TextViewUtils.setText(holder.tv_label_adapter_profile, item.title);
		TextViewUtils.setText(holder.tv_val_adapter_profile, item.data);
		return convertView;
	}
	
	private class Holder {
		
		TextView tv_label_adapter_profile;
		TextView tv_val_adapter_profile;
		
	}
	
}
