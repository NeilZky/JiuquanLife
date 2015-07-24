package com.jiuquanlife.module.forum.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.vo.forum.usercenter.UserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class UserListAdapter extends BaseListAdapter<UserInfo>{

	public UserListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_user, null);
			holder = new Holder();
			holder.iv_gender_user_adapter = (ImageView) convertView.findViewById(R.id.iv_gender_user_adapter);
			holder.civ_icon_user_adapter = (ImageView) convertView.findViewById(R.id.civ_icon_user_adapter);
			holder.tv_name_user_adapter = (TextView) convertView.findViewById(R.id.tv_name_user_adapter);
			holder.tv_sinature_user_adapter = (TextView) convertView.findViewById(R.id.tv_sinature_user_adapter);
			holder.tv_distance_user_adapter = (TextView) convertView.findViewById(R.id.tv_distance_user_adapter);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}	
		UserInfo item = getItem(position);
		TextViewUtils.setText(holder.tv_name_user_adapter, item.name);
		TextViewUtils.setText(holder.tv_sinature_user_adapter, item.signature);
		ImageLoader.getInstance().displayImage( UrlUtils.getPhotoUrl(String.valueOf(item.uid)),holder.civ_icon_user_adapter);
		
//		final ImageView mImageView = holder.civ_icon_user_adapter;
//        final String imageUrl =  UrlUtils.getPhotoUrl(String.valueOf(item.uid));
//        mImageView.setTag(imageUrl);
//        ImageLoader.getInstance().loadImage(imageUrl, new SimpleImageLoadingListener(){
//
//            @Override
//            public void onLoadingComplete(String imageUrl, View view,
//                                          Bitmap loadedImage) {
//                super.onLoadingComplete(imageUrl, view, loadedImage);
//                if (imageUrl.equals(mImageView.getTag())) {
//                    mImageView.setImageBitmap(loadedImage);
//                }
//            }
//        });
		
		if(item.gender == 0) {
			holder.iv_gender_user_adapter.setImageResource(R.drawable.ic_sex_man);
		} else {
			holder.iv_gender_user_adapter.setImageResource(R.drawable.ic_sex_woman);
		}
		if(StringUtils.isNullOrEmpty(item.distance)) {
			holder.tv_distance_user_adapter.setVisibility(View.GONE);
		} else {
			holder.tv_distance_user_adapter.setVisibility(View.VISIBLE);
			TextViewUtils.setText(holder.tv_distance_user_adapter, item.distance.substring(0, item.distance.indexOf('.')) + "รื");
		}
		return convertView;
	}
	
	private static class Holder {
	
		
		TextView tv_name_user_adapter;
		TextView tv_distance_user_adapter;
		TextView tv_sinature_user_adapter;
		ImageView civ_icon_user_adapter;
		ImageView iv_gender_user_adapter;
		
	}
	

}
