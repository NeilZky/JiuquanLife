package com.jiuquanlife.module.forum.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiuquanlife.vo.forum.PostDetail;
import com.jiuquanlife.vo.forum.Reply;

public class PostDetailAdapter extends BaseAdapter{

	private PostDetail postDetail;
	private ArrayList<Reply> replies;
	
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
		return null;
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
