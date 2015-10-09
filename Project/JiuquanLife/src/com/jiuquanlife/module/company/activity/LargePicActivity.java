package com.jiuquanlife.module.company.activity;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LargePicActivity extends BaseActivity {
	private ImageView iv;
	String path;
	private RelativeLayout rl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_large_img);
		iv = (ImageView) findViewById(R.id.iv_big);
		parseIntent();
		rl = (RelativeLayout) findViewById(R.id.rl_large);
		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
	}

	private void parseIntent() {
		Intent intent = getIntent();
		path = intent.getStringExtra("picPath");
		ImageLoader.getInstance().displayImage(Common.PIC_PREFX + path, iv,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {

						startProgressDialog("Мгдижа...");
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						stopProgressDialog();

					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						// TODO Auto-generated method stub
						stopProgressDialog();

					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						// TODO Auto-generated method stub

					}
				});
	}
}
