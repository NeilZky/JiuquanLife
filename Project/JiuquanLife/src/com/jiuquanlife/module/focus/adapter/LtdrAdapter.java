package com.jiuquanlife.module.focus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.vo.UserInfo;

/**
 * 
 *聚焦页面论坛达人适配器，展示头像和昵称
 */

public class LtdrAdapter extends BaseAdapter{
	
	private ArrayList<UserInfo> data;
	private Context context;
	private LayoutInflater inflater;
	
	public LtdrAdapter(Context context) {
		
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return data == null ? 0 :data.size();
	}

	@Override
	public UserInfo getItem(int position) {
		return data == null ? null :data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_ltdr, null);
			holder = new Holder();
			holder.nameTv = (TextView) convertView.findViewById(R.id.tv_name_adapter_ltdr);
			holder.potoIv = (ImageView) convertView.findViewById(R.id.iv_photo_adapter_ltdr);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		UserInfo userInfo = getItem(position);
		holder.nameTv.setText(userInfo.name);
		//TODO LOAD URLIMAGE
		holder.potoIv.setImageResource(R.drawable.photo_default);
		return convertView;
	}

	private static class Holder {
		
		TextView nameTv;
		ImageView potoIv;
		
	}
	
	public void refresh(ArrayList<UserInfo> data) {
		
		this.data = data;
		notifyDataSetChanged();
	}
	
}
