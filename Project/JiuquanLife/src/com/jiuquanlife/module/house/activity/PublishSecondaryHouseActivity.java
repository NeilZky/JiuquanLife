package com.jiuquanlife.module.house.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;

import com.jiuquanlife.R;
import com.jiuquanlife.adapter.PhotoAdapter;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.PhotoManager;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.utils.UploadUtils;
import com.jiuquanlife.view.LinearListView;

public class PublishSecondaryHouseActivity extends BaseActivity{
	
	private static final int REQUEST_CODE_CAMERA = 1;
	private PhotoAdapter photoAdapter;
	private PhotoManager photoManager = PhotoManager.getInstance();
	private LinearListView llv_photo_aps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		
		initViews();
	}

	private void initViews() {
		
		setContentView(R.layout.activity_publish_secondary);
		llv_photo_aps = (LinearListView) findViewById(R.id.llv_photo_aps);
		photoAdapter = new PhotoAdapter(this, PhotoManager.UPLOAD_PHOTO_PATH);
		photoAdapter.setLlv(llv_photo_aps);
		llv_photo_aps.setAdapter(photoAdapter);
		photoAdapter.refreshData();
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.iv_add_photo_aps:
			onClickAddPhoto();
			break;
		case R.id.btn_publish_aps:
			onClickPublish();
			break;
		default:
			break;
		}
	}

	private void onClickPublish() {
		
		new Thread() {
			
			@Override
			public void run() {
				super.run();
				File uploadFolder = new File(PhotoManager.UPLOAD_PHOTO_PATH);
				File[] files = uploadFolder.listFiles();
				if(files!=null) {
					for (File file : files) {
						String filePath = file.getPath();
						String res = UploadUtils.queryParam("http://www.5ijq.cn/App/House/addHouseImg", filePath, null);
						file.delete();
					}
					handler.sendEmptyMessage(0);
				}
			}
		}.start();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		
		@Override
		public void handleMessage(android.os.Message msg) {

			ToastHelper.showL("上传图片完成");
			
		};
		
	};
	
	
	private void onClickAddPhoto() {
		
		//TODO 拆分菜单，从相册里选择图片，图片多选控件等等
		startCamera();
	}

	private void startCamera() {
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri filePahtUri = Uri
				.fromFile(new File(PhotoManager.TEMP_PHOTO_BITMAP));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, filePahtUri);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Activity.RESULT_OK != resultCode) {
			return;
		}
		switch (requestCode) {
		case REQUEST_CODE_CAMERA:
			onResultCamera();
			break;
		default:
			break;
		}
	}
	
	private void onResultCamera() {
		
		photoManager.compressPicture(PhotoManager.TEMP_PHOTO_BITMAP, PhotoManager.UPLOAD_PHOTO_PATH);
		refreshThumbnailPhotos();
	}

	public void refreshThumbnailPhotos() {

		photoAdapter.refreshData();
	}
	
}
