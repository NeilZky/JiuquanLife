package com.jiuquanlife.utils;

import android.widget.ImageView;

import com.jiuquanlife.app.App;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageLoaderHelper {

	public static void loadEmpPhoto(ImageView imageView, String url) {

		ImageLoader.getInstance().displayImage(url, imageView,
				App.getInstance().empImageOption,
				new SimpleImageLoadingListener());
	}
	
	public static void loadRectPhoto(ImageView imageView, String url) {

		ImageLoader.getInstance().displayImage(url, imageView,
				App.getInstance().empImageOption,
				new SimpleImageLoadingListener());
	}
}
