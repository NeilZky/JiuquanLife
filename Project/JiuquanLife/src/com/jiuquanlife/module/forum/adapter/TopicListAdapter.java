package com.jiuquanlife.module.forum.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.view.LinearListView;
import com.jiuquanlife.vo.forum.Topic;

/**
 * 
 * °å¿éÁÐ±í
 */
public class TopicListAdapter extends BaseListAdapter<Topic> implements OnItemClickListener{

	public TopicListAdapter(Context context) {
		super(context);
	}
	
	private ArrayList<Boolean> childVisiableList;

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
			holder.ll_topic_content = (LinearLayout) convertView.findViewById(R.id.ll_topic_content);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Topic topic = getItem(position);
		TextViewUtils.setText(holder.tv_topic_name, topic.board_category_name);
		if(position>5) {
			holder.iv_topic_img.setImageResource(topicImgs[5]);
		} else {
			holder.iv_topic_img.setImageResource(topicImgs[position]);
		}
		if(childVisiableList.get(position)) {
			holder.iv_topic_arrow.setImageResource(R.drawable.ic_arrow_up);
			holder.ll_topic_border_list.setVisibility(View.VISIBLE);
		} else {
			holder.ll_topic_border_list.setVisibility(View.GONE);
			holder.iv_topic_arrow.setImageResource(R.drawable.ic_arrow_down);
		}
		BorderAdapter borderAdapter = new BorderAdapter(getContext());
		holder.llv_topic_border.setAdapter(borderAdapter);
		borderAdapter.refresh(topic.board_list);
		if(position < borderArrays.size()) {
			borderAdapter.setImgs(borderArrays.get(position));
		} else {
			borderAdapter.setImgs(borderArrays.get(borderArrays.size() - 1));
		}
		holder.llv_topic_border.notifyDataSetChanged();
		return convertView;
	}
	
	private int[] topicImgs = {R.drawable.ic_zwgl,R.drawable.ic_hdbk,R.drawable.ic_dcxs,R.drawable.ic_chwl, R.drawable.ic_rsds,R.drawable.ic_wnfw};
	
	private SparseArray<int[]> borderArrays;
	
	{
		borderArrays = new SparseArray<int[]>();
		int[] zwgl = {R.drawable.ic_zwgl_ltsw, R.drawable.ic_zwgl_bzjl};
		borderArrays.put(0, zwgl);
	}
	
	private static class Holder {

		TextView tv_topic_name;
		ImageView iv_topic_img;
		ImageView iv_topic_arrow;
		LinearListView llv_topic_border;
		LinearLayout ll_topic_border_list;
		LinearLayout ll_topic_content;
	}
	
	@Override
	public void refresh(ArrayList<Topic> data) {
		super.refresh(data);
		if(data == null) {
			childVisiableList = null;
			return;
		}
		
		if(childVisiableList == null) {
			childVisiableList = new ArrayList<Boolean>();
		}
		
		for(Topic temp : data) {
			childVisiableList.add(false);
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		boolean state = childVisiableList.get(position - 1);
		childVisiableList.set(position - 1, !state);
		notifyDataSetChanged();
	}
}
