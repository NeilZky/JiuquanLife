package com.jiuquanlife.app;

import io.rong.imkit.RongIM;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.baidu.mapapi.SDKInitializer;
import com.jiuquanlife.R;
import com.jiuquanlife.module.forum.activity.ForumTabActvity;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class App extends Application {

	private static App instance;
	public ForumTabActvity forumTabActvity;
	public DisplayImageOptions empImageOption;

	@Override
	public void onCreate() {

		super.onCreate();
		instance = this;
		SDKInitializer.initialize(getApplicationContext());
		initRongyun();
		// initImageLoader(this);
		initImageLoader();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		// Utils.getPermisson(getApplicationContext());
	}

	
	private void initRongyun() {

		RongIM.init(this);
		RongCloudEvent.init(this);
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		File cacheDir = StorageUtils.getOwnCacheDirectory(instance,
				"cache_folder");
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
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

	private static DisplayImageOptions options;

	private static void initOption() {

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_default_house)
				.showImageForEmptyUri(R.drawable.ic_default_house)
				.showImageOnFail(R.drawable.ic_default_house).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();

	}

	private void initImageLoader() {
		DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true).resetViewBeforeLoading(true)
				.considerExifParams(false).bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new SimpleBitmapDisplayer())
				.build();
		

		empImageOption = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.photo_default)
				.showImageOnLoading(R.drawable.photo_default)
				.showImageOnFail(R.drawable.photo_default)
				.cacheInMemory(true).cacheOnDisk(true)
				.resetViewBeforeLoading(true).considerExifParams(false)
				.displayer(new RoundedBitmapDisplayer(1000))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(400, 400)
				// default = device screen dimensions
				.diskCacheExtraOptions(400, 400, null)
				.threadPoolSize(5)
				// default Thread.NORM_PRIORITY - 1
				.threadPriority(Thread.NORM_PRIORITY)
				// default FIFO
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(5 * 1024 * 1024))
				.memoryCacheSize(5 * 1024 * 1024)
				.memoryCacheSizePercentage(13)
				// default
				.diskCache(
						new UnlimitedDiscCache(StorageUtils.getCacheDirectory(
								this, true)))
				// default
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				// default
				.imageDownloader(new BaseImageDownloader(this))
				// default
				.imageDecoder(new BaseImageDecoder(false))
				// default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// default
				.defaultDisplayImageOptions(imageOptions).build();

		ImageLoader.getInstance().init(config);
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
	public int imCount ;
	public int replyMsgCount ;

}
