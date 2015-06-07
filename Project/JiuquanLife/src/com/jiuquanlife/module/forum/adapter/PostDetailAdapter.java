package com.jiuquanlife.module.forum.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.TimeUtils;
import com.jiuquanlife.view.CircleImageView;
import com.jiuquanlife.view.LinearListView;
import com.jiuquanlife.vo.forum.PostDetail;
import com.jiuquanlife.vo.forum.Reply;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PostDetailAdapter extends BaseAdapter{

	private PostDetail postDetail;
	private ArrayList<Reply> replies;
	private View postDetailView;
	private Context context;
	private LayoutInflater inflater;
	private CircleImageView civ_apdd;
	private TextView tv_title_apdd;
	private TextView tv_name_apdd;
	private TextView tv_time_apdd;
	private LinearListView llv_content_apdd;
	private PostContentAdapter postContentAdapter;
	
	private ImageLoader imageLoader;
	
	
	public PostDetailAdapter(Context context) {
		
		this.context = context;
		inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
	}
	
	@Override
	public int getCount() {
		
		return replies == null ? 1 : replies.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		
		if(position == 0) {
			return postDetail;
		}
		return replies.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		
		if(position == 0) {
			return postDetail.topic_id;
		} else {
			return replies.get(position-1).reply_id;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(position == 0 ) {
			return getPostDetailView();
		} else {
			if(convertView == null||convertView.getId()!=R.id.ll_root_ar) {
				convertView = inflater.inflate(R.layout.adapter_reply, null);
			}
			Holder holder = getHolder(convertView);
			Reply reply = (Reply) getItem(position);
			imageLoader.displayImage(reply.icon, holder.civ_photo_ar);
			TextViewUtils.setText(holder.tv_name_ar, reply.reply_name);
			TextViewUtils.setText(holder.tv_user_type_ar, reply.userTitle);
			TextViewUtils.setText(holder.tv_stair_ar, reply.position + "Â¥");
			if(!StringUtils.isNullOrEmpty(reply.posts_date)) {
				long time = Long.parseLong(reply.posts_date);
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(time);
				TextViewUtils.setText(holder.tv_reply_date_ar, TimeUtils.getFormatedDateTime(c));
			}
			//TODO ÆÀÂÛÍ¼ÎÄ»ìÅÅ
			PostContentAdapter replyContentAdapter = new PostContentAdapter(context);
			replyContentAdapter.refresh(reply.reply_content);
			holder.llv_reply_content_ar.setAdapter(replyContentAdapter);
			holder.llv_reply_content_ar.notifyDataSetChanged();
			return convertView;
		}
	}
	
	
	private Holder getHolder(View convertView) {
		
		Holder holder = (Holder) convertView.getTag();
		if(holder == null) {
			holder = new Holder();
			holder.tv_name_ar = (TextView) convertView.findViewById(R.id.tv_name_ar);
			holder.civ_photo_ar = (ImageView) convertView.findViewById(R.id.civ_photo_ar);
			holder.llv_reply_content_ar = (LinearListView) convertView.findViewById(R.id.llv_reply_content_ar);
			holder.tv_reply_date_ar = (TextView) convertView.findViewById(R.id.tv_reply_date_ar);
			holder.tv_user_type_ar = (TextView) convertView.findViewById(R.id.tv_user_type_ar);
			holder.tv_stair_ar = (TextView) convertView.findViewById(R.id.tv_stair_ar);
		}
		return holder;
	}
	
	private View getPostDetailView() {
		
		if(postDetailView == null) {
			postDetailView = inflater.inflate(R.layout.adapter_post_detail_detail, null);
			tv_title_apdd = (TextView) postDetailView.findViewById(R.id.tv_title_apdd);
			civ_apdd = (CircleImageView) postDetailView.findViewById(R.id.civ_apdd);
			tv_name_apdd = (TextView) postDetailView.findViewById(R.id.tv_name_apdd);
			tv_time_apdd = (TextView) postDetailView.findViewById(R.id.tv_time_apdd);
			llv_content_apdd = (LinearListView) postDetailView.findViewById(R.id.llv_content_apdd);
			if(postContentAdapter == null) {
				postContentAdapter = new PostContentAdapter(context);
			}
			llv_content_apdd.setAdapter(postContentAdapter);
		}
		if(postDetail != null) {
			imageLoader.displayImage(postDetail.icon, civ_apdd);
			TextViewUtils.setText(tv_name_apdd, postDetail.user_nick_name);
			if(!StringUtils.isNullOrEmpty(postDetail.create_date)) {
				Calendar time = Calendar.getInstance();
				time.setTimeInMillis(Long.parseLong(postDetail.create_date));
				String dateTimeStr = TimeUtils.getFormatedDateTime(time);
				TextViewUtils.setText(tv_time_apdd, dateTimeStr);
			}
			tv_title_apdd.setText(postDetail.title);
			postContentAdapter.refresh(postDetail.content);
			llv_content_apdd.notifyDataSetChanged();
		}
		return postDetailView;
	}
	
	public void refresh(PostDetail postDetail, ArrayList<Reply> replies) {
		
		this.postDetail = postDetail;
		this.replies = replies;
		notifyDataSetChanged();
	}
	
	public void add(ArrayList<Reply> replies) {
		
		if(replies == null || replies.isEmpty()) {
			return;
		}
		
		if(this.replies == null) {
			this.replies = new ArrayList<Reply>();
		}
	
		this.replies.addAll(replies);
		notifyDataSetChanged();
	}
	
	private static class Holder {
		
		ImageView civ_photo_ar;
		TextView tv_name_ar;
		TextView tv_user_type_ar;
		TextView tv_stair_ar;
		TextView tv_reply_date_ar;
		LinearListView llv_reply_content_ar;
		
	}
	
}
