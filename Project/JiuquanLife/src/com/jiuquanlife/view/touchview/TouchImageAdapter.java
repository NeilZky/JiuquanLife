package com.jiuquanlife.view.touchview;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TouchImageAdapter extends PagerAdapter {

	private ArrayList<String> imageUrls;
	private ImageLoader imageLoader;
	
	public void refresh(ArrayList<String> imageUrls) {
		
		this.imageUrls  =  imageUrls;
		imageLoader = ImageLoader.getInstance();
	}
	
	
    @Override
    public int getCount() {
    	return imageUrls == null? 0 : imageUrls.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
    	
        TouchImageView img = new TouchImageView(container.getContext());
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageLoader.displayImage(imageUrls.get(position), img);
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

}
