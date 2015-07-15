package com.jiuquanlife.module.forum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.vo.forum.album.AlbumData;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AlbumAdapter extends BaseListAdapter<AlbumData>{


	public AlbumAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder = null;
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_album, null);
			holder = new Holder();
			holder.iv_album_adapter = (ImageView) convertView.findViewById(R.id.iv_album_adapter);
			holder.tv_title_album_adapter = (TextView) convertView.findViewById(R.id.tv_title_album_adapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		AlbumData item = getItem(position);
		ImageLoader.getInstance().displayImage(item.thumb_pic, holder.iv_album_adapter);
		TextViewUtils.setText(holder.tv_title_album_adapter, item.title);
		
		return convertView;
	}
	
	private class Holder {
		
		ImageView iv_album_adapter;
		TextView tv_title_album_adapter;
		
	}
}
