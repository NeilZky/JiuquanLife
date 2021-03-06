package com.jiuquanlife.module.forum.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.module.forum.activity.PostListActivity;
import com.jiuquanlife.vo.forum.Border;
import com.jiuquanlife.vo.forum.Topic;

/**
 * LinearListView ʹ��
 *
 */
public class BorderAdapter extends BaseListAdapter<Border>{

	private int[] imgs;
	private boolean selectBorder;
	private Topic topic;
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public void setSelectBorder(boolean selectBorder) {
		this.selectBorder = selectBorder;
	}

	public BorderAdapter(Context context) {
		super(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = getInflater().inflate(R.layout.adapter_border, null);
		TextView textView = (TextView) convertView.findViewById(R.id.tv_adapter_border);
		ImageView iv = (ImageView) convertView.findViewById(R.id.iv_adapter_border);
		final Border border = getItem(position);
		textView.setText(border.board_name);
		final int itemPosition = position;
		if(position >=imgs.length) {
			iv.setImageResource(imgs[imgs.length-1]);
		} else {
			iv.setImageResource(imgs[position]);
		}
		if(selectBorder) {
			convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent();
					intent.putExtra(PostListActivity.EXTRA_BORDER, border);
					intent.putExtra(PostListActivity.EXTRA_TOPIC, topic);
					((Activity)getContext()).setResult(Activity.RESULT_OK, intent);
					((Activity)getContext()).finish();
				}
			});
		} else {
			convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(getContext(), PostListActivity.class);
					intent.putExtra(PostListActivity.EXTRA_BORDER, border);
					intent.putExtra(PostListActivity.EXTRA_BORDER_POSITION, itemPosition);
					intent.putExtra(PostListActivity.EXTRA_BORDER_LIST, getData());
					getContext().startActivity(intent);
				}
			});
		}
		
		return convertView;
	}

	public void setImgs(int[] imgs) {
		this.imgs = imgs;
	}

}
