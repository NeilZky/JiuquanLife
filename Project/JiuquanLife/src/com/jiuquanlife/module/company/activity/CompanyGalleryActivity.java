package com.jiuquanlife.module.company.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.jiuquanlife.R;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.adapter.ImgAdapter;
import com.jiuquanlife.module.company.entity.ImgInfo;

public class CompanyGalleryActivity extends BaseActivity {
	private GridView gv;
	private ImgAdapter imgAdapter;
	ArrayList<ImgInfo> imgInfos;
	private LinearLayout ll_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_gallery);
		gv = (GridView) findViewById(R.id.gv_company_gallery);
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		parseIntent();
	}

	@SuppressWarnings("unchecked")
	private void parseIntent() {
		Intent intent = getIntent();
		imgInfos = intent.getParcelableArrayListExtra("imgInfos");
		System.out.println("imgInfos:" + imgInfos);
		if (null != imgInfos) {
			imgAdapter = new ImgAdapter(imgInfos, CompanyGalleryActivity.this);
			gv.setAdapter(imgAdapter);
		}
	}
}
