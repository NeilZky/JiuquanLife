package com.jiuquanlife.module.post.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.factory.BitmapUtilsFactory;
import com.jiuquanlife.vo.ContentInfo;
import com.lidroid.xutils.BitmapUtils;

public class ContentAdapter extends BaseAdapter{

	private ArrayList<ContentInfo> data;
	private LayoutInflater inflater;
	private BitmapUtils bitmapUtils;

	public ContentAdapter(Context context) {
		
		inflater = LayoutInflater.from(context);
		bitmapUtils = BitmapUtilsFactory.create(context);
	}
	
	@Override
	public int getCount() {
		
		return data == null? 0 :  data.size();
	}

	@Override
	public ContentInfo getItem(int position) {
		return data == null? null : data.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ContentInfo contentInfo = getItem(position);
		if("1".equals(contentInfo.isText)) {
			TextView tv = (TextView) inflater.inflate(R.layout.adapter_tv, null);
			tv.setText(contentInfo.text);
			return tv;
		} else if("0".equals(contentInfo.isText)) {
			ImageView iv = (ImageView) inflater.inflate(R.layout.adapter_iv, null);
			bitmapUtils.display(iv, CommonConstance.URL_PREFIX + contentInfo.photoUrl);
			return iv;
		}
		return null;
	}
	
	public void refresh(ArrayList<ContentInfo> data) {

		this.data = data;
	}
	
	
}
