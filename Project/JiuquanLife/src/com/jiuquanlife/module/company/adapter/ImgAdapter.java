package com.jiuquanlife.module.company.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.activity.LargePicActivity;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImgAdapter extends BaseAdapter {

	private List<ImgInfo> imgInfos;
	private Activity context;
	private LayoutInflater mInflater;

	public ImgAdapter(List<ImgInfo> imgInfos, Activity context) {
		super();
		this.imgInfos = imgInfos;
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgInfos == null ? 0 : imgInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return imgInfos.get(arg0);
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
			convertView = mInflater.inflate(R.layout.gallery_item, parent,
					false);
			holder = new ViewHolder();
			holder.iv_gallery = (ImageView) convertView
					.findViewById(R.id.iv_gallery_item);
			convertView.setTag(holder);
			;
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		final ImgInfo imgInfo = imgInfos.get(position);
		ImageLoader.getInstance().displayImage(
				Common.PIC_PREFX + imgInfo.getPic(), holder.iv_gallery);
		System.out.println("Img path:" + Common.PIC_PREFX + imgInfo.getPic());
		holder.iv_gallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, LargePicActivity.class);
				intent.putExtra("picPath", imgInfo.getPic());
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.getApplicationContext().startActivity(intent);
			}
		});

		return convertView;
	}

	static class ViewHolder {
		ImageView iv_gallery;
	}

}
