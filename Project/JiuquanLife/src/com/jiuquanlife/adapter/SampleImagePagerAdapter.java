package com.jiuquanlife.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SampleImagePagerAdapter extends PagerAdapter {

	private ArrayList<String> urls;
	private ViewPager viewPager;
	private Context context;
	private ImageLoader imageLoader;
	
	public SampleImagePagerAdapter(Context context, ViewPager viewPager) {
	
		this.context = context;
		this.viewPager = viewPager;
		imageLoader = ImageLoader.getInstance();
	}
	
	
	
	public void setUrls(ArrayList<String> urls) {
		this.urls = urls;
		if(urls!=null && !urls.isEmpty()) {
			viewPager.setCurrentItem(0);
			notifyDataSetChanged();
		}
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
	}
	
	@Override
	public int getCount() {
		
		return urls == null ? 0  : urls.size();
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
	
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		if(urls!=null) {
			String url = urls.get(position);
			if(!StringUtils.isNullOrEmpty(url)) {
				ImageView iv = new ImageView(context);
				iv.setScaleType(ScaleType.FIT_XY);
				((ViewPager) container).addView(iv, 0);
				imageLoader.displayImage( CommonConstance.PHOTO_URL_PREFIX + url, iv);
				return iv;
			}
		}
		return null;
	}



	
}
