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
import com.jiuquanlife.vo.house.HouseItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SecondaryHouseAdapter extends BaseListAdapter<HouseItem> {

	private ImageLoader imageLoader;
	
	public SecondaryHouseAdapter(Context context) {
		super(context);
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(R.layout.adapter_secondary_house, null);
			holder = new Holder();
			holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title_adapater_secondary_house);
			holder.towardsTv = (TextView) convertView.findViewById(R.id.tv_towawrds_adapater_secondary_house);
			holder.createdateTv = (TextView) convertView.findViewById(R.id.tv_create_date_adapter_secondary_house);
			holder.housePriceTv = (TextView) convertView.findViewById(R.id.tv_price_adapter_secondary_house);
			holder.fromTypeTv = (TextView) convertView.findViewById(R.id.tv_fromtype_adapter_secondary_house);
			holder.img = (UrlTagImageView) convertView
					.findViewById(R.id.iv_img_adapater_secondary_house);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		HouseItem houseItem = getItem(position);
		holder.titleTv.setText(houseItem.title);
		holder.towardsTv.setText(houseItem.towards);
		holder.fromTypeTv.setText("¸öÈË");
		holder.housePriceTv.setText(String.valueOf(houseItem.housePrice));
		holder.createdateTv.setText(String.valueOf(houseItem.dateline));

		if(houseItem.img!=null && !StringUtils.isNullOrEmpty(houseItem.img.pic)) {
			final String url = "http://www.5ijq.cn/Public/Uploads/" +houseItem.img.pic;
//			holder.img.setTag(url);
//			holder.img.loadImage(url, new OnBitmapLoadedListener() {
//				
//				@Override
//				public void onBitmapLoaded(ImageView imageView, Bitmap bitmap) {
//					
//					if (imageView.getTag() != null
//							&& imageView.getTag().equals(url)) {
//						imageView.setImageBitmap(bitmap);
//					} else {
//						imageView.setImageResource(R.drawable.ic_launcher);
//					}
//				}
//			});
			imageLoader.displayImage(url, holder.img, App.getOptions());
		} else {
			holder.img.setImageResource(R.drawable.ic_empty);
		}
		return convertView;
	}

	
	private static class Holder {

		TextView titleTv;
		TextView towardsTv;
		TextView fromTypeTv;
		TextView housePriceTv;
		TextView createdateTv;
		UrlTagImageView img;
		
	}

}
