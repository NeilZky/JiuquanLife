package com.jiuquanlife.module.home.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jiuquanlife.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SplashAdapter extends PagerAdapter{

	private List<View> viewLists;
	
	public SplashAdapter(Context context) {
		
		viewLists = new ArrayList<View>();
		addView(R.drawable.splash_1, context);
		addView(R.drawable.splash_2, context);
		addView(R.drawable.splash_3, context);
		addView(R.drawable.splash_4, context);
		addView(R.drawable.splash_5, context);
		addView(R.drawable.splash_6, context);
	}
	
	public void addView(int resId, Context context) {
		
		ImageView image = new ImageView(context);
		image.setScaleType(ScaleType.FIT_XY);
		image.setImageResource(resId);
		viewLists.add(image);
	}
	
	
	public void addView(View v) {
		
		viewLists.add(v);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() { // 获得size
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View view, int position, Object object) // 销毁Item
	{
		((ViewPager) view).removeView(viewLists.get(position));
	}

	@Override
	public Object instantiateItem(View view, int position) // 实例化Item
	{
		((ViewPager) view).addView(viewLists.get(position), 0);
		return viewLists.get(position);
	}
}
