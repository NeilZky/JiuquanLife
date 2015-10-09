package com.jiuquanlife.module.company.adapter;

import java.util.List;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.adapter.CommentAdapter.ViewHolder;
import com.jiuquanlife.module.company.entity.CommentInfo;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.jiuquanlife.module.company.entity.ProductInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	private List<ProductInfo> productList;
	private Activity context;
	private LayoutInflater mInflater;

	public ProductAdapter(List<ProductInfo> productList, Activity mContext) {
		super();
		this.productList = productList;
		this.context = mContext;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == productList ? 0 : productList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return productList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.company_product_item,
					parent, false);
			holder = new ViewHolder();
			holder.iv_company = (ImageView) convertView
					.findViewById(R.id.itemsIcon);
			holder.tv_desc = (TextView) convertView
					.findViewById(R.id.tv_productdesc);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_productprice);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_productname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ProductInfo productInfo = productList.get(position);
		holder.tv_desc.setText(productInfo.getDiscription());
		holder.tv_price.setText("¼Û¸ñ:" + productInfo.getPrice());
		holder.tv_name.setText(productInfo.getProductName());
		ImgInfo imgInfo = productInfo.getProductImg();
		if (null != imgInfo)
			ImageLoader.getInstance().displayImage(
					Common.PIC_PREFX + imgInfo.getPic(), holder.iv_company);

		return convertView;
	}

	static class ViewHolder {
		TextView tv_price, tv_name, tv_desc;
		ImageView iv_company;
	}

}
