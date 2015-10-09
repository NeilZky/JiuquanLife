package com.jiuquanlife.module.company.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuquanlife.R;

public class HotSearchAdapter extends BaseAdapter {

	private Activity activity;
	private List<String> name;
	private LayoutInflater mInflater;

	public HotSearchAdapter(List<String> name, Activity context) {
		super();
		this.name = name;
		this.activity = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public String getItem(int arg0) {
		// TODO Auto-generated method stub
		return name.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.hot_search_item, parent,
					false);
			holder = new ViewHolder();
			holder.titleTv = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String tmpName = name.get(position);
		holder.titleTv.setText(tmpName);

		return convertView;
	}

	static class ViewHolder {
		TextView titleTv;
	}

}
