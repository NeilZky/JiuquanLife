package com.jiuquanlife.module.house.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.utils.StringUtils;
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
			holder.img = (ImageView) convertView
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
		holder.createdateTv.setText(String.valueOf(houseItem.createdate));
		if(houseItem.img!=null && !StringUtils.isNullOrEmpty(houseItem.img.pic)) {
			imageLoader.displayImage("http://www.5ijq.cn/Public/Uploads/" +houseItem.img.pic,holder.img);
			
		}
		return convertView;
	}

	private static class Holder {

		TextView titleTv;
		TextView towardsTv;
		TextView fromTypeTv;
		TextView housePriceTv;
		TextView createdateTv;
		ImageView img;
		
	}

}
