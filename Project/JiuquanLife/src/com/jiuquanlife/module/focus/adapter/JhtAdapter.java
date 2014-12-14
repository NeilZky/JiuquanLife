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
import com.jiuquanlife.vo.PostInfo;

/**
 * 
 * ¾«»ªÌû
 */
public class JhtAdapter extends BaseAdapter{

	private ArrayList<PostInfo> data;
	private Context context;
	private LayoutInflater inflater;
	
	public JhtAdapter(Context context) {
		
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return data == null ? 0 :data.size();
	}

	@Override
	public PostInfo getItem(int position) {
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
			convertView = inflater.inflate(R.layout.adapter_jht, null);
			holder = new Holder();
			holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title_jht_adapter);
			holder.forwardCountTv = (TextView) convertView.findViewById(R.id.tv_forward_count_jht_adapter);
			holder.replyCountTv = (TextView) convertView.findViewById(R.id.tv_reply_count_jht_adapter);
			holder.timeTv = (TextView) convertView.findViewById(R.id.tv_time_jht_adapter);
			holder.photoIv = (ImageView) convertView.findViewById(R.id.iv_photo_jht_dapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		PostInfo postInfo = getItem(position);
		holder.titleTv.setText(postInfo.subject);
		holder.forwardCountTv.setText(String.valueOf(postInfo.forwardCount));
		holder.replyCountTv.setText(String.valueOf(postInfo.replyCount));
		holder.timeTv.setText(postInfo.time);
		//TODO LOAD URLIMAGE
		holder.photoIv.setImageResource(R.drawable.photo_default);
		return convertView;
	}

	private static class Holder {
		
		TextView titleTv;
		ImageView photoIv;
		TextView forwardCountTv;
		TextView replyCountTv;
		TextView timeTv;
	}
	
	public void refresh(ArrayList<PostInfo> data) {
		
		this.data = data;
		notifyDataSetChanged();
	}
	
}
