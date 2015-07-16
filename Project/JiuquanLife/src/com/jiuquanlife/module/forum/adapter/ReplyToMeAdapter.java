package com.jiuquanlife.module.forum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.TimeUtils;
import com.jiuquanlife.vo.forum.replytome.Reply;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReplyToMeAdapter extends BaseListAdapter<Reply>{

	public ReplyToMeAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder = null;
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_reply_to_me, null);
			holder = new Holder();
			holder.tv_reply_nick_name_adapter = (TextView) convertView.findViewById(R.id.tv_reply_nick_name_adapter);
			holder.tv_reply_date_adapter = (TextView) convertView.findViewById(R.id.tv_reply_date_adapter);
			holder.tv_reply_content_adapter = (TextView) convertView.findViewById(R.id.tv_reply_content_adapter);
			holder.iv_icon_adapter = (ImageView) convertView.findViewById(R.id.iv_icon_adapter);
			holder.et_reply_back_adapter = (EditText) convertView.findViewById(R.id.et_reply_back_adapter);
			holder.btn_jump_post_adapter = (Button) convertView.findViewById(R.id.btn_jump_post_adapter);
			holder.btn_submit_reply_adapter = (Button) convertView.findViewById(R.id.btn_submit_reply_adapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Reply item = getItem(position);
		TextViewUtils.setText(holder.tv_reply_nick_name_adapter, item.reply_nick_name);
		TextViewUtils.setText(holder.tv_reply_date_adapter, TimeUtils.convertToTime(item.replied_date));
		TextViewUtils.setText(holder.tv_reply_content_adapter, TimeUtils.convertToTime(item.reply_content));
		ImageLoader.getInstance().displayImage(item.icon, holder.iv_icon_adapter);
		return convertView;
	}
	
	private class Holder {
		
		Button btn_jump_post_adapter;
		Button btn_submit_reply_adapter;
		TextView tv_reply_nick_name_adapter;
		TextView tv_reply_date_adapter;
		TextView tv_reply_content_adapter;
		ImageView iv_icon_adapter;
		EditText et_reply_back_adapter;
		
	}

}
