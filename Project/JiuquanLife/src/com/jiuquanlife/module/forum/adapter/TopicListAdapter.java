package com.jiuquanlife.module.forum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.view.LinearListView;
import com.jiuquanlife.vo.forum.Topic;

/**
 * 
 * °å¿éÁÐ±í
 */
public class TopicListAdapter extends BaseListAdapter<Topic>{

	public TopicListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder = null;
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_toplic_list, null);
			holder = new Holder();
			holder.iv_topic_img = (ImageView) convertView.findViewById(R.id.iv_topic_img);
			holder.iv_topic_arrow = (ImageView) convertView.findViewById(R.id.iv_topic_arrow);
			holder.tv_topic_name = (TextView) convertView.findViewById(R.id.tv_topic_name);
			holder.tv_topic_name = (TextView) convertView.findViewById(R.id.tv_topic_name);
			holder.llv_topic_border = (LinearListView) convertView.findViewById(R.id.llv_topic_border);
			holder.ll_topic_border_list = (LinearLayout) convertView.findViewById(R.id.ll_topic_border_list);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	private static class Holder {

		TextView tv_topic_name;
		ImageView iv_topic_img;
		ImageView iv_topic_arrow;
		LinearListView llv_topic_border;
		LinearLayout ll_topic_border_list;
		
	}
}
