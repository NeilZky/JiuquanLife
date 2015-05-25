package com.jiuquanlife.module.house.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.vo.house.Community;

public class SimpleCommunityAdapter extends BaseListAdapter<Community> {

	public SimpleCommunityAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_tv, null);
			holder = new Holder();
			holder.tv = (TextView) convertView;
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Community item = getItem(position);
		holder.tv.setText(item.communityName);
		return convertView;
	}

	private static class Holder {

		TextView tv;
	}

}
