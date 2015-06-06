package com.jiuquanlife.module.forum.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.adapter_reply, null);
			}
			return convertView;
		}
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
	
}
