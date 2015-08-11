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
import com.nostra13.universalimageloader.core.ImageLoader;

public class AgentAdapter extends BaseListAdapter<Agent> {

	private ImageLoader imageLoader;
	
	public AgentAdapter(Context context) {
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
		Agent houseItem = getItem(position);
		holder.trueName.setText(houseItem.trueName);
		holder.companyName.setText(houseItem.compName);
		holder.shopName.setText(houseItem.shopName);
		if(houseItem.headimg!=null && !StringUtils.isNullOrEmpty(houseItem.headimg.pic)) {
			final String url = "http://www.5ijq.cn/Public/Uploads/" +houseItem.headimg.pic;
			holder.img.setTag(url);
			imageLoader.displayImage(url, holder.img, App.getOptions());
		} else {
			holder.img.setImageResource(R.drawable.ic_default_house);
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
