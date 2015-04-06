package com.jiuquanlife.app;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.jiuquanlife.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class App extends Application{

	private static App instance;
	
	@Override
	public void onCreate() {

		super.onCreate();
		instance = this;
		initImageLoader(this);
	}
	

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		File cacheDir = StorageUtils.getOwnCacheDirectory(instance, "cache_folder");
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.discCache(new UnlimitedDiskCache(cacheDir));
		config.discCacheFileNameGenerator(new HashCodeFileNameGenerator());
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
		initOption();
	}
	
	private static DisplayImageOptions options ;
	
	private static void initOption() {
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
		
	}
	
	public static App getInstance() {
		
		return instance;
	}


	public static DisplayImageOptions getOptions() {
		return options;
	}


	public static void setOptions(DisplayImageOptions options) {
		App.options = options;
	}
	
}
