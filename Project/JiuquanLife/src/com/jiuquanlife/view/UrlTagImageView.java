package com.jiuquanlife.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

public class UrlTagImageView extends ImageView {

	private  String url;
	private Bitmap bitmap;
	private static final int MSG_GET_BITMAP = 1;
	private HandlerThread taskThread;
	private Handler taskHandler;
	private boolean isInit = true;
	private int reqWidth;
	private int reqHeight;
	private OnBitmapLoadedListener onBitmapLoadedListener;
	
	public UrlTagImageView(Context context, AttributeSet attrs) {
		
		super(context, attrs);

	}
	
	public void loadImage(String url, int reqWidth, int reqHeight, OnBitmapLoadedListener onBitmapLoadedListener) {
		
		this.reqHeight = reqHeight;
		this.reqWidth = reqWidth;
		loadImage(url, onBitmapLoadedListener);
	}
	

	
	public void loadImage(String url, OnBitmapLoadedListener onBitmapLoadedListener) {
		
		this.onBitmapLoadedListener = onBitmapLoadedListener;
		if(isInit){
			taskThread = new HandlerThread("loadPhoto");
			taskThread.start();
			taskHandler = new Handler(taskThread.getLooper());
			isInit = false;
		}
		
		if(bitmap!=null && this.url == url){
			onBitmapLoadedListener.onBitmapLoaded(this, bitmap);
		} else {
			this.url = url;
			taskHandler.postDelayed(loadImageRunnable, 100L);
		}
	}
	
	
	public Bitmap getBitmap() {

		return bitmap;
	}
	

	@SuppressLint("HandlerLeak")
	private Handler uiHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_GET_BITMAP:
				bitmap = (Bitmap) msg.obj;
				if(onBitmapLoadedListener !=null) {
					onBitmapLoadedListener.onBitmapLoaded(UrlTagImageView.this, bitmap);
				}
				break;
			default:
				break;
			}
		}
	};
	
	private Runnable loadImageRunnable = new Runnable() {
		
		@Override
		public void run() {
			Bitmap bitmap ;
			if(UrlTagImageView.this.reqHeight !=0 && UrlTagImageView.this.reqWidth !=0) {
				 bitmap = PhotoCacheUtils.getBitmap(url ,UrlTagImageView.this.reqWidth,UrlTagImageView.this.reqHeight);
			} else {
				bitmap = PhotoCacheUtils.getBitmap(url ,getWidth(), getHeight());
			}
			if(bitmap!=null){
				Message msg = uiHandler.obtainMessage();
				msg.what = MSG_GET_BITMAP;
				msg.obj = bitmap;
				uiHandler.sendMessage(msg);
			}
		}
	};
	
	public interface OnBitmapLoadedListener{
		
		public void onBitmapLoaded(ImageView imageView,Bitmap bitmap); 
	}
}
