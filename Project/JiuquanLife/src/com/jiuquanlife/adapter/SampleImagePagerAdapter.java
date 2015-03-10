package com.jiuquanlife.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.utils.BitmapHelper;
import com.jiuquanlife.utils.StringUtils;
import com.lidroid.xutils.BitmapUtils;

public class SampleImagePagerAdapter extends PagerAdapter{

	private BitmapUtils bitmapUtils;
	private ArrayList<String> urls;
	private ViewPager viewPager;
	private Context context;
	
	public SampleImagePagerAdapter(Context context, ViewPager viewPager) {
	
		this.context = context;
		this.viewPager = viewPager;
		bitmapUtils = BitmapHelper.getBitmapUtils(context
				.getApplicationContext());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	}
	
	
	
	public void setUrls(ArrayList<String> urls) {
		this.urls = urls;
		if(urls!=null && !urls.isEmpty()) {
			viewPager.setCurrentItem(0);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		
		return urls == null ? 0  : urls.size();
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
	
		return false;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		if(urls!=null) {
			String url = urls.get(position);
			if(!StringUtils.isNullOrEmpty(url)) {
				ImageView iv = new ImageView(context);
				iv.setScaleType(ScaleType.FIT_XY);
				((ViewPager) container).addView(iv, 0);
				bitmapUtils.display(iv, CommonConstance.PHOTO_URL_PREFIX + url);
				return iv;
			}
		}
		return null;
	}
	
}
