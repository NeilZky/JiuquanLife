package com.jiuquanlife.module.house.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.app.App;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.view.UrlTagImageView;
import com.jiuquanlife.view.UrlTagImageView.OnBitmapLoadedListener;
import com.jiuquanlife.vo.house.Agent;
import com.jiuquanlife.vo.house.Community;
import com.jiuquanlife.vo.house.Img;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CommunityWithPhotoAdapter extends BaseListAdapter<Community> {

	private ImageLoader imageLoader;
	
	public CommunityWithPhotoAdapter(Context context) {
		super(context);
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_agent, null);
			holder = new Holder();
			holder.trueName = (TextView) convertView.findViewById(R.id.tv_first_line);
			holder.companyName = (TextView) convertView.findViewById(R.id.tv_second_line);
			holder.shopName = (TextView) convertView.findViewById(R.id.tv_third_line);
			holder.img = (UrlTagImageView) convertView
					.findViewById(R.id.iv_img_adapater);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Community houseItem = getItem(position);
		holder.trueName.setText(houseItem.communityName);
		holder.companyName.setText(houseItem.shequName);
		holder.shopName.setText(houseItem.address);
		if(houseItem.img!=null ) {
			Img img = houseItem.img;
			if(img != null && !StringUtils.isNullOrEmpty(img.pic)) {
				final String url = "http://www.5ijq.cn/Public/Uploads/" +img.pic;
				imageLoader.displayImage(url, holder.img, App.getOptions());
			}
		} else {
			holder.img.setImageResource(R.drawable.ic_empty);
		}
		return convertView;
	}

	
	private static class Holder {

		TextView trueName;
		TextView companyName;
		TextView shopName;
		UrlTagImageView img;
	}

}
