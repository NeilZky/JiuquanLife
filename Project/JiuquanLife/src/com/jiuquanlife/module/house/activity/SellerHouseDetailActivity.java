package com.jiuquanlife.module.house.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.SampleImagePagerAdapter;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.vo.house.HouseDetailInfo;
import com.jiuquanlife.vo.house.Img;

public class SellerHouseDetailActivity extends BaseActivity {

	public static final String INTENT_KEY_HOUSE_ID = "INTENT_KEY_HOUSE_ID";
	
	private ViewPager photoVp;
	private SampleImagePagerAdapter photoAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		initViews();
		getData();
	}

	private void initViews() {

		setContentView(R.layout.activity_seller_house_detail);
		photoVp = (ViewPager) findViewById(R.id.vp_ashd);
		photoAdapter = new SampleImagePagerAdapter(this, photoVp);
	}

	private void getData() {

		String houseid = getIntent().getStringExtra(INTENT_KEY_HOUSE_ID);
		RequestHelper.getInstance().postRequest(SellerHouseDetailActivity.this,
				"http://www.5ijq.cn/App/House/getHoustById/id/" + houseid,
				null, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						HouseDetailInfo info = GsonUtils.toObj(response,
								HouseDetailInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// ÇëÇóÊý¾ÝÊ§°Ü
							return;
						}
						fillViews(info);
					}
				});
	}

	private void fillViews(HouseDetailInfo info) {
		
		ArrayList<Img> imgs = info.data.img;
		if(imgs!=null) {
			ArrayList<String> urls = new ArrayList<String>();
			for(Img img : imgs) {
				if(img.pic!=null) {
					urls.add(img.pic);
					photoAdapter.setUrls(urls);
					photoVp.setAdapter(photoAdapter);
				}
			}
		}
	}

}
