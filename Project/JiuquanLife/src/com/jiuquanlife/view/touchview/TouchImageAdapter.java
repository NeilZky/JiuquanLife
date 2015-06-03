package com.jiuquanlife.view.touchview;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jiuquanlife.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class TouchImageAdapter extends PagerAdapter implements OnPageChangeListener{

	private ArrayList<String> imageUrls;
	private ImageLoader imageLoader;
	private LinearLayout dotLl;
	private ArrayList<ImageView> dots;
	private Context context;
	private ProgressBar pb;
	private int currentItem;

	public TouchImageAdapter(Context context, LinearLayout dotLl, ProgressBar pb) {
	
		this.context = context;
		this.dotLl = dotLl;
		this.pb = pb;
		imageLoader = ImageLoader.getInstance();
	}
	
	public void refresh(ArrayList<String> imageUrls) {
		
		this.imageUrls  =  imageUrls;
		initDot();
	}
	
	
	private void initDot() {

		if (imageUrls == null) {
			return;
		}
		
		dotLl.removeAllViews();
		dots = new ArrayList<ImageView>();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				20, 20);
		layoutParams.setMargins(4, 3, 4, 3);

		for (int i = 0; i < imageUrls.size(); i++) {
			ImageView dot = new ImageView(context);
			dot.setLayoutParams(layoutParams);
			dots.add(dot);
			dot.setTag(i);
			if (i == 0) {
				dot.setBackgroundResource(R.drawable.dotc);
			} else {
				dot.setBackgroundResource(R.drawable.dotn);
			}
			dotLl.addView(dot);
		}
	}
	
    @Override
    public int getCount() {
    	return imageUrls == null? 0 : imageUrls.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
    	
        TouchImageView img = new TouchImageView(container.getContext());
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final String url = imageUrls.get(position);
        imageLoader.displayImage(url, img, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				
				pb.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				
				pb.setVisibility(View.GONE);
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				pb.setVisibility(View.GONE);
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				
				pb.setVisibility(View.GONE);
			}
		});
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
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
	}
	
}
