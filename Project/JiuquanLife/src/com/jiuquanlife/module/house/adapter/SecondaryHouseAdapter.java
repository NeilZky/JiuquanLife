package com.jiuquanlife.module.house.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.app.App;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.view.UrlTagImageView;
import com.jiuquanlife.vo.house.HouseItem;
import com.nostra13.universalimageloader.core.ImageLoader;

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
			holder.areaTv = (TextView) convertView.findViewById(R.id.tv_area_adapater_secondary_house);
			holder.img = (UrlTagImageView) convertView
					.findViewById(R.id.iv_img_adapater_secondary_house);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		HouseItem houseItem = getItem(position);
		holder.titleTv.setText(houseItem.title);
		if(StringUtils.isNullOrEmpty(houseItem.towards)) {
			holder.towardsTv.setVisibility(View.GONE);
		} else {
			holder.towardsTv.setVisibility(View.VISIBLE);
		}
		StringBuffer areaSb = new StringBuffer();
		if(!StringUtils.isNullOrEmpty(houseItem.addressName)) {
			areaSb.append(houseItem.addressName + "-");
		}
		if(!StringUtils.isNullOrEmpty(houseItem.subAddressName)) {
			areaSb.append(houseItem.subAddressName + "-");
		}
		if(!StringUtils.isNullOrEmpty(houseItem.communityName)) {
			areaSb.append(houseItem.communityName + "-");
		}
		if(!StringUtils.isNullOrEmpty(houseItem.houseLayout)) {
			areaSb.append(houseItem.houseLayout + "-");
		}
		if(areaSb.length() > 0) {
			String area = areaSb.toString().substring(0, areaSb.length() - 2);
			holder.areaTv.setVisibility(View.VISIBLE);
			TextViewUtils.setText(holder.areaTv, area);
		} else {
			holder.areaTv.setVisibility(View.GONE);
		}
		
		TextViewUtils.setText(holder.towardsTv, houseItem.towards);
		String fromType = "未知-";
		if("1".equals(houseItem.fromType)) {
			fromType = "个人-";
		} else {
			fromType = "中介-";
		}
		holder.fromTypeTv.setText(fromType);
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
			holder.img.setImageResource(R.drawable.ic_default_house);
		}
		return convertView;
	}

	
	private static class Holder {

		TextView titleTv;
		TextView towardsTv;
		TextView fromTypeTv;
		TextView housePriceTv;
		TextView createdateTv;
		TextView areaTv;
		UrlTagImageView img;
		
	}

}
