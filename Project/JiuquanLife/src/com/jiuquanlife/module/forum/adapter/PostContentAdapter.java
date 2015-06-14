package com.jiuquanlife.module.forum.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.module.base.PictureViewPagerActivity;
import com.jiuquanlife.vo.forum.Content;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class PostContentAdapter extends BaseListAdapter<Content>{
	
	private ImageLoader imageLoader;
	private Context context;
	
	public PostContentAdapter(Context context) {
		super(context);
		this.context = context;
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Holder holder = null;
		if(convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_post_content, null);
			holder = new Holder();
			holder.tv_apc = (TextView) convertView.findViewById(R.id.tv_apc);
			holder.iv_apc = (ImageView) convertView.findViewById(R.id.iv_apc);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Content content = getItem(position);
		if(content.type == 1) {
			holder.tv_apc.setVisibility(View.GONE);
			holder.iv_apc.setVisibility(View.VISIBLE);
			final String originalInfo = content.originalInfo;
			imageLoader.displayImage(content.originalInfo, holder.iv_apc, new SimpleImageLoadingListener() {
				
				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					ImageView imageView = (ImageView) view;
					imageView.setImageBitmap(loadedImage);
				}
			});
			holder.iv_apc.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					ArrayList<String> imgs = new ArrayList<String>();
					imgs.add(originalInfo);
					Intent intent = new Intent(context, PictureViewPagerActivity.class);
					intent.putExtra(PictureViewPagerActivity.EXTRA_IMAGE_URLS, imgs);
					intent.putExtra(PictureViewPagerActivity.EXTRA_CURRENT_ITEM, 0);
					context.startActivity(intent);
				}
			});
		} else if(content.type == 0){
			holder.tv_apc.setVisibility(View.VISIBLE);
			holder.iv_apc.setVisibility(View.GONE);
			holder.tv_apc.setText(content.infor);
		} else {
			holder.tv_apc.setVisibility(View.GONE);
			holder.iv_apc.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private static class Holder {
		
		TextView tv_apc;
		ImageView iv_apc;
	}
	
	
}
