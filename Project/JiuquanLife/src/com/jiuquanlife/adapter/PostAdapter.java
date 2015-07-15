package com.jiuquanlife.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.PictureViewPagerActivity;
import com.jiuquanlife.module.forum.activity.PostDetailActivity;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TimeUtils;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.vo.forum.PostItem;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * 帖子列表
 */
public class PostAdapter extends BaseAdapter implements OnItemClickListener{

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
			holder.iv_praise_post_adapter = (ImageView) convertView.findViewById(R.id.iv_praise_post_adapter);
			holder.tv_name_post_adapter = (TextView) convertView.findViewById(R.id.tv_name_post_adapter);
			holder.tv_title_post_adapter = (TextView) convertView.findViewById(R.id.tv_title_post_adapter);
			holder.tv_subject_post_adapter = (TextView) convertView.findViewById(R.id.tv_subject_post_adapter);
			holder.tv_praise_count_post_adapter = (TextView) convertView.findViewById(R.id.tv_praise_count_post_adapter);
			holder.tv_reply_count_jht_adapter = (TextView) convertView.findViewById(R.id.tv_reply_count_post_adapter);
			holder.tv_date_post_adapter = (TextView) convertView.findViewById(R.id.tv_date_post_adapter);
			holder.iv_photo_post_dapte = (ImageView) convertView.findViewById(R.id.iv_photo_post_dapter);
			holder.ll_images_post_adapter = (LinearLayout) convertView.findViewById(R.id.ll_images_post_adapter);
			holder.iv_sex_post_adapter = (ImageView) convertView.findViewById(R.id.iv_sex_post_adapter);
			holder.iv_collect_post_adapter = (ImageView) convertView.findViewById(R.id.iv_collect_post_adapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final PostItem postItem = getItem(position);
		holder.tv_title_post_adapter.setText(postItem.title);
		holder.tv_subject_post_adapter.setText(postItem.subject);
		holder.tv_praise_count_post_adapter.setText(String.valueOf(postItem.vote));
		holder.tv_reply_count_jht_adapter.setText(String.valueOf(postItem.replies));
		imageLoader.displayImage( UrlUtils.getPhotoUrl(String.valueOf(postItem.user_id)),holder.iv_photo_post_dapte);
		if(postItem.imageList == null || postItem.imageList.isEmpty()) {
			holder.ll_images_post_adapter.setVisibility(View.GONE);
		} else {
			holder.ll_images_post_adapter.setVisibility(View.VISIBLE);
			int i=0;
			for(; i < postItem.imageList.size() && i<4; i++) {
				ImageView iv = (ImageView) holder.ll_images_post_adapter.getChildAt(i);
				iv.setVisibility(View.VISIBLE);
				imageLoader.displayImage(postItem.imageList.get(i),iv);
				final int currentIndex = i;
				iv.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent(context, PictureViewPagerActivity.class);
						intent.putExtra(PictureViewPagerActivity.EXTRA_IMAGE_URLS, postItem.imageList);
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
		holder.tv_name_post_adapter.setText(postItem.user_nick_name);
		if(postItem.gender == 1) {
			holder.iv_sex_post_adapter.setVisibility(View.VISIBLE);
			holder.iv_sex_post_adapter.setImageResource(R.drawable.ic_sex_man);
		} else if(postItem.gender  == 2) {
			holder.iv_sex_post_adapter.setVisibility(View.VISIBLE);
			holder.iv_sex_post_adapter.setImageResource(R.drawable.ic_sex_woman);
		} else {
			holder.iv_sex_post_adapter.setVisibility(View.GONE);
		}
		if(!StringUtils.isNullOrEmpty(postItem.last_reply_date)) {
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(Long.parseLong(postItem.last_reply_date));
			holder.tv_date_post_adapter.setText(TimeUtils.getFormatedDateTime(date));
		}
		
		holder.iv_praise_post_adapter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(logined()) {
					addPraise(postItem);
				}
			}
		});
		
		holder.iv_collect_post_adapter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(logined()) {
					collect(postItem);
				}
			}
		});
		
		return convertView;
	}
	
	protected void collect(PostItem postItem) {
		
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userfavorite");
		map.put("action", "favorite");
		map.put("id", String.valueOf(postItem.topic_id));
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		RequestHelper.getInstance().getRequestMap(context, UrlConstance.FORUM_URL, map, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				System.out.println(response);
				//TODO 更改收藏状态
			}
		});
	}

	private boolean logined() {
		
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		if(user == null) {
			Intent intent = new Intent(context, LoginActivity.class);
			context.startActivity(intent);
			return false;
		} 
		return true;
	}
	
	private void addPraise(final PostItem postItem) {
		
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "forum/support");
		map.put("type", "topic");
		map.put("tid", String.valueOf(postItem.topic_id));
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		RequestHelper.getInstance().getRequestMap(context, UrlConstance.FORUM_URL, map, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				postItem.vote++;
				notifyDataSetChanged();
			}
		});
	}
	
	private static class Holder {
		
		ImageView iv_collect_post_adapter;
		ImageView iv_praise_post_adapter;
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		if(position == 0) {
			return;
		}
		PostItem postItem = getItem(position-1);
		Intent intent = new Intent(context, PostDetailActivity.class);
		intent.putExtra(PostDetailActivity.EXTRA_TOPIC_ID, postItem.topic_id);
		context.startActivity(intent);
	}
	
}
