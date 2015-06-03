package com.jiuquanlife.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.PictureViewPagerActivity;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TimeUtils;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.vo.forum.PostItem;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * Ìû×ÓÁÐ±í
 */
public class PostAdapter extends BaseAdapter{

	private ArrayList<PostItem> data;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private Context context;
	
	public PostAdapter(Context context) {
		
		this.context = context;
		inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
	}
	
	@Override
	public int getCount() {
		return data == null ? 0 :data.size();
	}

	@Override
	public PostItem getItem(int position) {
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
			holder.tv_name_post_adapter = (TextView) convertView.findViewById(R.id.tv_name_post_adapter);
			holder.tv_title_post_adapter = (TextView) convertView.findViewById(R.id.tv_title_post_adapter);
			holder.tv_subject_post_adapter = (TextView) convertView.findViewById(R.id.tv_subject_post_adapter);
			holder.tv_praise_count_post_adapter = (TextView) convertView.findViewById(R.id.tv_praise_count_post_adapter);
			holder.tv_reply_count_jht_adapter = (TextView) convertView.findViewById(R.id.tv_reply_count_post_adapter);
			holder.tv_date_post_adapter = (TextView) convertView.findViewById(R.id.tv_date_post_adapter);
			holder.iv_photo_post_dapte = (ImageView) convertView.findViewById(R.id.iv_photo_post_dapter);
			holder.ll_images_post_adapter = (LinearLayout) convertView.findViewById(R.id.ll_images_post_adapter);
			holder.iv_sex_post_adapter = (ImageView) convertView.findViewById(R.id.iv_sex_post_adapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final PostItem postInfo = getItem(position);
		holder.tv_title_post_adapter.setText(postInfo.title);
		holder.tv_subject_post_adapter.setText(postInfo.subject);
		holder.tv_praise_count_post_adapter.setText(String.valueOf(postInfo.vote));
		holder.tv_reply_count_jht_adapter.setText(String.valueOf(postInfo.replies));
		imageLoader.displayImage( UrlUtils.getPhotoUrl(String.valueOf(postInfo.user_id)),holder.iv_photo_post_dapte);
		if(postInfo.imageList == null || postInfo.imageList.isEmpty()) {
			holder.ll_images_post_adapter.setVisibility(View.GONE);
		} else {
			holder.ll_images_post_adapter.setVisibility(View.VISIBLE);
			int i=0;
			for(; i < postInfo.imageList.size() && i<4; i++) {
				ImageView iv = (ImageView) holder.ll_images_post_adapter.getChildAt(i);
				iv.setVisibility(View.VISIBLE);
				imageLoader.displayImage(postInfo.imageList.get(i),iv);
				final int currentIndex = i;
				iv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent(context, PictureViewPagerActivity.class);
						intent.putExtra(PictureViewPagerActivity.EXTRA_IMAGE_URLS, postInfo.imageList);
						intent.putExtra(PictureViewPagerActivity.EXTRA_CURRENT_ITEM, currentIndex);
						context.startActivity(intent);
					}
				});
			}
			for(int j = i; j < 4; j++) {
				ImageView iv = (ImageView) holder.ll_images_post_adapter.getChildAt(j);
				iv.setVisibility(View.INVISIBLE);
			}
		}
		holder.tv_name_post_adapter.setText(postInfo.user_nick_name);
		if(postInfo.gender == 1) {
			holder.iv_sex_post_adapter.setVisibility(View.VISIBLE);
			holder.iv_sex_post_adapter.setImageResource(R.drawable.ic_sex_man);
		} else if(postInfo.gender  == 2) {
			holder.iv_sex_post_adapter.setVisibility(View.VISIBLE);
			holder.iv_sex_post_adapter.setImageResource(R.drawable.ic_sex_woman);
		} else {
			holder.iv_sex_post_adapter.setVisibility(View.GONE);
		}
		if(!StringUtils.isNullOrEmpty(postInfo.last_reply_date)) {
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(Long.parseLong(postInfo.last_reply_date));
			holder.tv_date_post_adapter.setText(TimeUtils.getFormatedDateTime(date));
		}
		return convertView;
	}

	private static class Holder {
		
		ImageView iv_sex_post_adapter;
		TextView tv_name_post_adapter;
		TextView tv_title_post_adapter;
		TextView tv_subject_post_adapter;
		ImageView iv_photo_post_dapte;
		TextView tv_praise_count_post_adapter;
		TextView tv_reply_count_jht_adapter;
		TextView tv_date_post_adapter;
		LinearLayout ll_images_post_adapter;
	}
	
	public void refresh(ArrayList<PostItem> data) {
		
		this.data = data;
		notifyDataSetChanged();
	}
	
	public void addList(ArrayList<PostItem> addedDatas) {
		
		if(this.data == null) {
			this.data = new ArrayList<PostItem>();
		}
		this.data.addAll(addedDatas);
		notifyDataSetChanged();
	}
	
}
