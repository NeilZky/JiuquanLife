package com.jiuquanlife.module.forum.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.vo.forum.Content;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class PostContentAdapter extends BaseListAdapter<Content>{
	
	private ImageLoader imageLoader;
	
	public PostContentAdapter(Context context) {
		super(context);
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
			imageLoader.displayImage(content.originalInfo, holder.iv_apc, new SimpleImageLoadingListener() {
				
				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					ImageView imageView = (ImageView) view;
					int height = loadedImage.getHeight();
					int width = loadedImage.getWidth();
					int viewHegiht = height * imageView.getWidth() / width;
					android.view.ViewGroup.LayoutParams params = imageView.getLayoutParams();  
				    params.height=viewHegiht;  
				    imageView.setLayoutParams(params);
					imageView.setImageBitmap(loadedImage);
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
