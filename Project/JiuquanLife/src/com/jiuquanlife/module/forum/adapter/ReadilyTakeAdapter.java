package com.jiuquanlife.module.forum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.vo.forum.readliytake.ReadliyTakePost;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReadilyTakeAdapter extends BaseListAdapter<ReadliyTakePost>{

	public ReadilyTakeAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_readliy_take, null);
			holder = new Holder();
			holder.iv_adapter_readliy_take = (ImageView) convertView.findViewById(R.id.iv_adapter_readliy_take);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		ReadliyTakePost item = getItem(position);
		if(item.imgList!=null && !item.imgList.isEmpty()) {
			String url = CommonConstance.URL_PREFIX + item.imgList.get(0);
			ImageLoader.getInstance().displayImage(url, holder.iv_adapter_readliy_take);
		}
		return convertView;
	}
	
	private static class Holder {
		
		ImageView iv_adapter_readliy_take;
	}
	
}
