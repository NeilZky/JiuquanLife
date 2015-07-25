package com.jiuquanlife.module.forum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.vo.forum.readliytake.ReadliyTakePost;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReadliyTakeAdapter extends BaseListAdapter<ReadliyTakePost>{

	public ReadliyTakeAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_readliy_take, null);
			holder = new Holder();
			holder.iv_adapter_readliy_take = (ImageView) convertView.findViewById(R.id.iv_adapter_readliy_take);
			holder.iv_icon_adapter_readliy = (ImageView) convertView.findViewById(R.id.iv_icon_adapter_readliy);
			holder.tv_title_adapter_readliy_take = (TextView) convertView.findViewById(R.id.tv_title_adapter_readliy_take);
			holder.tv_name_adapter_readliy = (TextView) convertView.findViewById(R.id.tv_name_adapter_readliy);
			holder.tv_time_adapter_readliy = (TextView) convertView.findViewById(R.id.tv_time_adapter_readliy);
			holder.tv_view_count_adapter_readliy = (TextView) convertView.findViewById(R.id.tv_view_count_adapter_readliy);
			holder.tv_reply_count_adapter_readliy = (TextView) convertView.findViewById(R.id.tv_reply_count_adapter_readliy);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		ReadliyTakePost item = getItem(position);
		if(item.imgList!=null && !item.imgList.isEmpty()) {
			String url = CommonConstance.URL_PREFIX + item.imgList.get(0);
			ImageLoader.getInstance().displayImage(url, holder.iv_adapter_readliy_take);
		}
		ImageLoader.getInstance().displayImage(UrlUtils.getPhotoUrl(item.authorid+""), holder.iv_icon_adapter_readliy);
		TextViewUtils.setText(holder.tv_name_adapter_readliy, item.author);
		TextViewUtils.setText(holder.tv_time_adapter_readliy, item.dateline);
		TextViewUtils.setText(holder.tv_name_adapter_readliy, item.author);
		TextViewUtils.setText(holder.tv_view_count_adapter_readliy, item.views + "");
		TextViewUtils.setText(holder.tv_reply_count_adapter_readliy, item.replies + "");
		TextViewUtils.setText(holder.tv_title_adapter_readliy_take, item.title);
		return convertView;
	}
	
	private static class Holder {
		
		ImageView iv_adapter_readliy_take;
		ImageView iv_icon_adapter_readliy;
		TextView tv_title_adapter_readliy_take;
		TextView tv_name_adapter_readliy;
		TextView tv_time_adapter_readliy;
		TextView tv_view_count_adapter_readliy;
		TextView tv_reply_count_adapter_readliy;
	}
	
}
