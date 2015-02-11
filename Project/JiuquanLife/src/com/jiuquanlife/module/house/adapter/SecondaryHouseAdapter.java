package com.jiuquanlife.module.house.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.BaseListAdapter;
import com.jiuquanlife.utils.BitmapHelper;
import com.jiuquanlife.vo.house.HouseItem;
import com.lidroid.xutils.BitmapUtils;

public class SecondaryHouseAdapter extends BaseListAdapter<HouseItem> {

	private BitmapUtils bitmapUtils;
	
	public SecondaryHouseAdapter(Context context) {
		super(context);
		bitmapUtils = BitmapHelper.getBitmapUtils(context
				.getApplicationContext());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
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
//		bitmapUtils.display(holder.img, "www.5ijq.cn/Public/Uploads/" +houseItem.img.pic);
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
