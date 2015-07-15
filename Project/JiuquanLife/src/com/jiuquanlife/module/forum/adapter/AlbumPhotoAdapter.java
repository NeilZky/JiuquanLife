package com.jiuquanlife.module.forum.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.module.base.PictureViewPagerActivity;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.TimeUtils;
import com.jiuquanlife.vo.forum.photo.AlbumPhotoData;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AlbumPhotoAdapter extends BaseListAdapter<AlbumPhotoData>{


	public AlbumPhotoAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder = null;
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_album_photo, null);
			holder = new Holder();
			holder.iv_album_photo_adapter = (ImageView) convertView.findViewById(R.id.iv_album_photo_adapter);
			holder.tv_title_album_photo_adapter = (TextView) convertView.findViewById(R.id.tv_title_album_photo_adapter);
			holder.tv_date_album_photo_adapter = (TextView) convertView.findViewById(R.id.tv_date_album_photo_adapter);
			holder.tv_reply_count_album_photo_adapter = (TextView) convertView.findViewById(R.id.tv_reply_count_album_photo_adapter);
			holder.tv_view_count_album_photo_adapter = (TextView) convertView.findViewById(R.id.tv_view_count_album_photo_adapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		AlbumPhotoData item = getItem(position);
		ImageLoader.getInstance().displayImage(item.thumb_pic, holder.iv_album_photo_adapter);
		TextViewUtils.setText(holder.tv_title_album_photo_adapter, item.title);
		TextViewUtils.setText(holder.tv_reply_count_album_photo_adapter, item.replies + "");
		TextViewUtils.setText(holder.tv_view_count_album_photo_adapter, item.hot + "");
		Calendar c = Calendar.getInstance();
		if(!StringUtils.isNullOrEmpty(item.release_date)) {
			c.setTimeInMillis(Long.parseLong(item.release_date));
			TextViewUtils.setText(holder.tv_date_album_photo_adapter, TimeUtils.getFormatDate(c));
		}
		holder.iv_album_photo_adapter.setTag(item);
		holder.iv_album_photo_adapter.setOnClickListener(onClickListener);
		return convertView;
	}
	
	private OnClickListener onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			AlbumPhotoData item = (AlbumPhotoData) v.getTag();
			if(item!=null && !StringUtils.isNullOrEmpty(item.origin_pic)) {
				ArrayList<String> url = new ArrayList<String>();
				url.add(item.origin_pic);
				Intent intent = new Intent(getContext(), PictureViewPagerActivity.class);
				intent.putExtra(PictureViewPagerActivity.EXTRA_IMAGE_URLS, url);
				getContext().startActivity(intent);
			}
		}
	};
	
	private class Holder {
		
		ImageView iv_album_photo_adapter;
		TextView tv_title_album_photo_adapter;
		TextView tv_date_album_photo_adapter;
		TextView tv_reply_count_album_photo_adapter;
		TextView tv_view_count_album_photo_adapter;
		
	}
}
