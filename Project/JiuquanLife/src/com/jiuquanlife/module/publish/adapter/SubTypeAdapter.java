package com.jiuquanlife.module.publish.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.publish.entity.TypeInfo;

public class SubTypeAdapter extends BaseAdapter {

	private Context activity;
	private List<TypeInfo> name;
	private LayoutInflater mInflater;

	public SubTypeAdapter(List<TypeInfo> name, Context context) {
		super();
		this.name = name;
		this.activity = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == name ? 0 : name.size();
	}

	@Override
	public TypeInfo getItem(int arg0) {
		// TODO Auto-generated method stub
		return name.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	int pos=-1;
	public void setMark(int position){
		pos=position;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.common_gird_item, parent,
					false);
			holder = new ViewHolder();
			holder.titleTv = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(pos==position)
		{
			convertView.setBackgroundColor(activity.getResources().getColor(R.color.white));
		}else{
			convertView.setBackground(activity.getResources().getDrawable(R.drawable.search_gridview_item));
		}
		TypeInfo tmpName = name.get(position);
		holder.titleTv.setText(tmpName.toString());

		return convertView;
	}

	static class ViewHolder {
		TextView titleTv;
	}

}
