package com.jiuquanlife.module.company.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuquanlife.R;

public class SearchHistoryAdapter extends BaseAdapter {
	private List<String> history = new ArrayList<String>();
	private Activity activity;
	private LayoutInflater mInflater;

	public SearchHistoryAdapter(Set<String> history, Activity activity) {
		super();
		if (null != history) {
			for (Iterator it = history.iterator(); it.hasNext();) {

				String value = (String) it.next();
				this.history.add(value);
			}
		}

		System.out.println(" SearchHistoryAdapter history:" + history);
		this.activity = activity;
		this.mInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return history.size();
	}

	@Override
	public String getItem(int arg0) {
		// TODO Auto-generated method stub
		return history.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("callGetView:" + position);
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.company_search_item,
					parent, false);
			holder = new ViewHolder();
			holder.tv_history = (TextView) convertView
					.findViewById(R.id.tvitem_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String title = history.get(position);
		System.out.println("set history:" + title);
		holder.tv_history.setText(title);
		return convertView;
	}

	static class ViewHolder {
		TextView tv_history;
	}

}
