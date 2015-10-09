package com.jiuquanlife.module.publish.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.publish.entity.TypeGroupInfo;

public class PublishTypeAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<TypeGroupInfo> infos;

	public PublishTypeAdapter(List<TypeGroupInfo> infos, Context context) {
		super();
		this.infos = infos;
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public TypeGroupInfo getItem(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	int pos;

	public void setSelectedItem(int position) {
		if (position != pos) {
			pos = position;
			notifyDataSetChanged();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.publish_type_item, parent,
					false);
			holder = new ViewHolder();
			holder.iv_pl_item_icon = (ImageView) convertView
					.findViewById(R.id.iv_type);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_type);
			holder.iv_mark = (ImageView) convertView.findViewById(R.id.iv_mark);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		TypeGroupInfo groupInfo = infos.get(position);
		if (pos == position) {
			holder.iv_mark.setVisibility(View.VISIBLE);
		} else {
			holder.iv_mark.setVisibility(View.INVISIBLE);
		}
		holder.tv_name.setText(groupInfo.getTypeName());
		String type = groupInfo.getTypeName();
		if (type.equals("招聘")) {
			holder.iv_pl_item_icon.setBackgroundResource(R.drawable.job_type);
		} else if (type.equals("出租")) {
			holder.iv_pl_item_icon
					.setBackgroundResource(R.drawable.rental_type);
		} else if (type.equals("转让")) {
			holder.iv_pl_item_icon.setBackgroundResource(R.drawable.zhuan_type);
		} else if (type.equals("推广")) {
			holder.iv_pl_item_icon.setBackgroundResource(R.drawable.tui_type);
		} else if (type.equals("其他")) {
			holder.iv_pl_item_icon.setBackgroundResource(R.drawable.other_type);
		} else if (type.equals("出售")) {
			holder.iv_pl_item_icon.setBackgroundResource(R.drawable.shou_type);
		} else if (type.equals("全部")) {
			holder.iv_pl_item_icon.setBackgroundResource(R.drawable.all_type);
		}
		return convertView;
	}

	public static class ViewHolder {
		ImageView iv_pl_item_icon, iv_mark;
		TextView tv_name;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
