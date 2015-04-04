package com.jiuquanlife.module.focus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.vo.PhotoInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FocusTopAdapter extends PagerAdapter implements OnPageChangeListener{

	private Context context;
	private ArrayList<PhotoInfo> photoInfos;
	private LinearLayout dotLl;
	private ArrayList<ImageView> dots;
	private ViewPager viewPager;
	private int currentItem;
	private Handler handler = new Handler();
	private TextView titleTv;
	private ImageLoader imageLoader;
	
	public FocusTopAdapter(Context context, LinearLayout dotLl, ViewPager viewPager, TextView titleTv) {

		this.context = context;
		this.dotLl = dotLl;
		this.viewPager = viewPager;
		this.titleTv = titleTv;
		imageLoader = ImageLoader.getInstance();
	}

	public void setPhotoInfos(ArrayList<PhotoInfo> photoInfos) {

		this.photoInfos = photoInfos;
		initDot();
		currentItem = 0;
		if(photoInfos!=null) {
			titleTv.setText(photoInfos.get(0).title);
			viewPager.setCurrentItem(currentItem);
			notifyDataSetChanged();
			handler.postDelayed(autoChangeItemRunnable, 3000L);
		}
	}
	
	public PhotoInfo getItem(int position) {
		
		if(this.photoInfos!=null) {
			return this.photoInfos.get(position);
		}
		return null;
	}
	
	private Runnable autoChangeItemRunnable = new Runnable() {
		
		@Override
		public void run() {
			
			if(currentItem == (dots.size() -1)) {
				currentItem = 0;
			} else {
				++currentItem;
			}
			viewPager.setCurrentItem(currentItem);
		}
	};

	// ≥ı ºªØdot ”Õº
	private void initDot() {

		if (photoInfos == null) {
			return;
		}
		
		dotLl.removeAllViews();
		dots = new ArrayList<ImageView>();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				40, 40);
		layoutParams.setMargins(4, 3, 4, 3);

		for (int i = 0; i < photoInfos.size(); i++) {
			ImageView dot = new ImageView(context);

			dot.setLayoutParams(layoutParams);
			dots.add(dot);
			dot.setOnClickListener(onClickListener);
			dot.setTag(i);
			if (i == 0) {
				dot.setBackgroundResource(R.drawable.dotc);
			} else {
				dot.setBackgroundResource(R.drawable.dotn);
			}
			dotLl.addView(dot);
		}
	}

	private OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			
			int length = dots.size();
			for(int i = 0 ; i < length; i++) {
				ImageView temp = dots.get(i);
				if(temp.getTag() == v.getTag()) {
					temp.setBackgroundResource(R.drawable.dotc);
					viewPager.setCurrentItem(i);
				} else {
					temp.setBackgroundResource(R.drawable.dotn);
				}
			}
		}
	};

	@Override
	public int getCount() {
		return photoInfos == null ? 0 : photoInfos.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		// ((ViewPager) container).removeView(items.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		ImageView iv = new ImageView(context);
		iv.setScaleType(ScaleType.FIT_XY);
		((ViewPager) container).addView(iv, 0);
		PhotoInfo photoInfo = photoInfos.get(position);
		imageLoader.displayImage(photoInfo.url, iv);
		iv.setOnClickListener(onClickImageListener);
		return iv;
	}

	private OnClickListener onClickImageListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			if(onClickItemListener!=null) {
				onClickItemListener.onClick(v);
			}
		}
	};
	
	private OnClickListener onClickItemListener;
	
	public void setOnClickItemListener(OnClickListener onClickItemListener) {
		this.onClickItemListener = onClickItemListener;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		
		dots.get(position).setBackgroundResource(R.drawable.dotc);
		int length = dots.size();
		for (int i = 0; i < length; i++) {
			if (position != i) {
				dots.get(i).setBackgroundResource(R.drawable.dotn);
			} 
		}
		titleTv.setText(photoInfos.get(position).title);
		currentItem = position;
		handler.removeCallbacks(autoChangeItemRunnable);
		handler.postDelayed(autoChangeItemRunnable, 3000L);
	}
	
	public PhotoInfo getCurrentItem() {
		
		return getItem(currentItem);
	}
}
