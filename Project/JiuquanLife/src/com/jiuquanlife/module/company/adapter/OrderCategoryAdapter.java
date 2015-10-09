package com.jiuquanlife.module.company.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.entity.IntelligenSortInfo;

public class OrderCategoryAdapter extends BaseAdapter {
	private List<IntelligenSortInfo> intelligenSortInfos;
	private Context context;
	private LayoutInflater mInflater;

	public OrderCategoryAdapter(List<IntelligenSortInfo> intelligenSortInfos,
			Context context) {
		super();
		this.intelligenSortInfos = intelligenSortInfos;
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private int orderId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return intelligenSortInfos.size();
	}

	@Override
	public IntelligenSortInfo getItem(int arg0) {
		// TODO Auto-generated method stub
		return intelligenSortInfos.get(arg0);
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
			convertView = mInflater.inflate(R.layout.category_select_item,
					parent, false);
			holder = new ViewHolder();
			holder.rightIv = (ImageView) convertView
					.findViewById(R.id.haschild);
			holder.titleTv = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		IntelligenSortInfo intelligenSortInfo = intelligenSortInfos
				.get(position);
		if (intelligenSortInfo.getId() == orderId) {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.item_selected_color));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.item_normal_color));
		}
		holder.titleTv.setText(intelligenSortInfo.getIntelligentSort());
		return convertView;
	}

	static class ViewHolder {
		ImageView rightIv;
		TextView titleTv;
	}

}
