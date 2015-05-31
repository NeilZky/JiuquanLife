package com.jiuquanlife.module.focus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.vo.PostInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * ¾«»ªÌû
 */
public class JhtAdapter extends BaseAdapter{

	private ArrayList<PostInfo> data;
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	
	public JhtAdapter(Context context) {
		
		inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
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
			convertView = inflater.inflate(R.layout.adapter_post, null);
			holder = new Holder();
			holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title_post_adapter);
			holder.praiseCountTv = (TextView) convertView.findViewById(R.id.tv_praise_count_post_adapter);
			holder.replyCountTv = (TextView) convertView.findViewById(R.id.tv_reply_count_post_adapter);
			holder.timeTv = (TextView) convertView.findViewById(R.id.tv_date_post_adapter);
			holder.photoIv = (ImageView) convertView.findViewById(R.id.iv_photo_post_dapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		PostInfo postInfo = getItem(position);
		holder.titleTv.setText(postInfo.subject);
		holder.praiseCountTv.setText(String.valueOf(postInfo.views));
		holder.replyCountTv.setText(String.valueOf(postInfo.replies));
		holder.timeTv.setText(postInfo.dateline);
		imageLoader.displayImage( UrlUtils.getPhotoUrl(postInfo.authorid),holder.photoIv);
		return convertView;
	}

	private static class Holder {
		
		TextView titleTv;
		ImageView photoIv;
		TextView praiseCountTv;;
		TextView replyCountTv;
		TextView timeTv;
	}
	
	public void refresh(ArrayList<PostInfo> data) {
		
		this.data = data;
		notifyDataSetChanged();
	}
	
}
