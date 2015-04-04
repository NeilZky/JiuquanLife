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
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.vo.UserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 *聚焦页面论坛达人适配器，展示头像和昵称
 */

public class LtdrAdapter extends BaseAdapter{
	
	private ArrayList<UserInfo> data;
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	
	public LtdrAdapter(Context context) {
		
		inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();

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
		holder.nameTv.setText(userInfo.username);
		//TODO LOAD URLIMAGE
		String photoUrl = UrlUtils.getPhotoUrl(userInfo.uid);
		imageLoader.displayImage(photoUrl, holder.potoIv);
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
