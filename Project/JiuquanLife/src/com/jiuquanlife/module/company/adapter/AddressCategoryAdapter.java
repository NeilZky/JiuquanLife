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
import com.jiuquanlife.module.company.entity.AddressGroupInfo;
import com.jiuquanlife.module.company.entity.AddressInfo;

public class AddressCategoryAdapter<T extends AddressInfo> extends BaseAdapter {
	private List<T> addressInfos;
	private Context context;
	private LayoutInflater mInflater;
	private int distanceId;

	public AddressCategoryAdapter(List<T> addressInfos, Context context) {
		super();
		this.addressInfos = addressInfos;
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return addressInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return addressInfos.get(arg0);
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
		AddressInfo addressInfo = addressInfos.get(position);
		if (addressInfo.getAid() == distanceId) {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.item_selected_color));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.item_normal_color));
		}
		holder.titleTv.setText(addressInfo.getAddressName());
		if (addressInfo instanceof AddressGroupInfo) {
			holder.rightIv.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	public int getDistanceId() {
		return distanceId;
	}

	public void setDistanceId(int distanceId) {
		this.distanceId = distanceId;
	}

	static class ViewHolder {
		ImageView rightIv;
		TextView titleTv;
	}

}
