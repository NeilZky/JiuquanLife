package com.jiuquanlife.module.house.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.expand.ExpandTabView;
import com.jiuquanlife.view.expand.ViewLeft;
import com.jiuquanlife.view.expand.ViewMiddle;
import com.jiuquanlife.view.expand.ViewRight;
import com.jiuquanlife.vo.FocusInfo;
import com.jiuquanlife.vo.PhotoInfo;
import com.jiuquanlife.vo.convertor.ConvertUtils;
import com.jiuquanlife.vo.house.GetSellHouseListInfo;

public class SecondaryHouseActivity extends BaseActivity{

	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewLeft priceTab;
	private ViewRight viewRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		initView();
		initVaule();
		initListener();
		getData();
	}
	
	private void initView() {
		
		setContentView(R.layout.activity_secondary);
		expandTabView = (ExpandTabView) findViewById(R.id.etv_secondary_house);
		viewLeft = new ViewLeft(this);
		priceTab = new ViewLeft(this);
		viewRight = new ViewRight(this);
	}
	
	private void initVaule() {
		
		mViewArray.add(viewLeft);
		mViewArray.add(priceTab);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("距离");
		mTextArray.add("价格");
		mTextArray.add("距离");
		expandTabView.setValue(mTextArray, mViewArray);
//		expandTabView.setTitle(viewLeft.getShowText(), 0);
//		expandTabView.setTitle(viewMiddle.getShowText(), 1);
//		expandTabView.setTitle(viewRight.getShowText(), 2);
	}

	private void getData() {
		
		RequestHelper.getInstance().postRequest(SecondaryHouseActivity.this,
				"http://www.5ijq.cn/App/House/getSellHouseList", null,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

						GetSellHouseListInfo info = GsonUtils.toObj(response,
								GetSellHouseListInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// 请求数据失败
							return;
						}
					}
				});
	}
	

	private void initListener() {
		
		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewLeft, showText);
			}
		});
		
		
		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewRight, showText);
			}
		});
		
	}
	
	private void onRefresh(View view, String showText) {
		
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(SecondaryHouseActivity.this, showText, Toast.LENGTH_SHORT).show();

	}
	
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void onBackPressed() {
		
		if (!expandTabView.onPressBack()) {
			finish();
		}
		
	}
	
}
