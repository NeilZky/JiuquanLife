package com.jiuquanlife.module.house.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.SecondaryHouseAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.expand.ExpandTabView;
import com.jiuquanlife.view.expand.ViewLeft;
import com.jiuquanlife.view.expand.ViewRight;
import com.jiuquanlife.vo.house.AddressRange;
import com.jiuquanlife.vo.house.AreaRange;
import com.jiuquanlife.vo.house.FromType;
import com.jiuquanlife.vo.house.GetSellHouseListData;
import com.jiuquanlife.vo.house.GetSellHouseListInfo;
import com.jiuquanlife.vo.house.HouseItem;
import com.jiuquanlife.vo.house.LayoutRange;
import com.jiuquanlife.vo.house.PriceRange;

public class SecondaryHouseActivity extends BaseActivity {

	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft addressTab;
	private ViewLeft priceTab;
	private ViewLeft layoutTab;
	private ViewLeft areaTab;
	private ViewLeft fromTypeTab;
	private ViewRight viewRight;
	private SecondaryHouseAdapter adapter;
	private ListView houseListLv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		initView();
		// initVaule();
		// initListener();
		getData();
	}

	private void initView() {

		setContentView(R.layout.activity_secondary);
		expandTabView = (ExpandTabView) findViewById(R.id.etv_secondary_house);
		houseListLv = (ListView) findViewById(R.id.lv_seconary_house);
		adapter = new SecondaryHouseAdapter(this);
		houseListLv.setAdapter(adapter);
		houseListLv.setOnItemClickListener(onItemClickListener);
		// priceTab = new ViewLeft(this);
		// viewRight = new ViewRight(this);
	}

	private void initVaule() {

		mViewArray.add(addressTab);
		mViewArray.add(priceTab);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("距离");
		mTextArray.add("价格");
		mTextArray.add("距离");
		expandTabView.setValue(mTextArray, mViewArray);
	}
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			HouseItem houseItem = adapter.getItem(position);
			Intent intent = new Intent(SecondaryHouseActivity.this, SellerHouseDetailActivity.class);
			intent.putExtra(SellerHouseDetailActivity.INTENT_KEY_HOUSE_ID, houseItem.houseid);
			startActivity(intent);
		}
	};
	
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
						initMenu(info.data);
						adapter.refresh(info.data.houseList);
					}
				});
	}

	private void initMenu(GetSellHouseListData data) {
		
		if(data == null){
			return;
		}
		
		ArrayList<String> mTextArray = new ArrayList<String>();
		if ( data.addressList != null
				&& !data.addressList.isEmpty()) {
			ArrayList<String> label = new ArrayList<String>();
			for (AddressRange temp : data.addressList) {
				label.add(temp.addressName);
			}
			addressTab = new ViewLeft(this, label, data.addressList);
			mTextArray.add("区域");
			mViewArray.add(addressTab);
		}
		
		if ( data.priceRangeList != null
				&& !data.priceRangeList.isEmpty()) {
			ArrayList<String> label = new ArrayList<String>();
			for (PriceRange temp : data.priceRangeList) {
				label.add(temp.priceRange);
			}
			priceTab = new ViewLeft(this, label, data.priceRangeList);
			mTextArray.add("总价");
			mViewArray.add(priceTab);
		}
		
		if ( data.houseLayoutRangeList != null
				&& !data.houseLayoutRangeList.isEmpty()) {
			ArrayList<String> label = new ArrayList<String>();
			for (LayoutRange temp : data.houseLayoutRangeList) {
				label.add(temp.layoutRange);
			}
			layoutTab = new ViewLeft(this, label, data.houseLayoutRangeList);
			mTextArray.add("厅室");
			mViewArray.add(layoutTab);
		}
		
		if ( data.areaRangeList != null
				&& !data.areaRangeList.isEmpty()) {
			ArrayList<String> label = new ArrayList<String>();
			for (AreaRange temp : data.areaRangeList) {
				label.add(temp.areaRange);
			}
			areaTab = new ViewLeft(this, label, data.areaRangeList);
			mTextArray.add("面积");
			mViewArray.add(areaTab);
		}
		
		if ( data.fromTypeList != null
				&& !data.fromTypeList.isEmpty()) {
			ArrayList<String> label = new ArrayList<String>();
			for (FromType temp : data.fromTypeList) {
				label.add(temp.fromType);
			}
			fromTypeTab = new ViewLeft(this, label, data.fromTypeList);
			mTextArray.add("来源");
			mViewArray.add(fromTypeTab);
		}
		
		expandTabView.setValue(mTextArray, mViewArray);

		
		
		initListener();

	}

	private void initListener() {

		if (addressTab != null) {
			addressTab.setOnSelectListener(new ViewLeft.OnSelectListener() {

				@Override
				public void getValue(Object obj) {
					AddressRange aa = (AddressRange) obj;
					onRefresh(addressTab, aa.addressName);
				}
			});
		}

		// viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {
		//
		// @Override
		// public void getValue(String distance, String showText) {
		// onRefresh(viewRight, showText);
		// }
		// });
		//
	}

	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(SecondaryHouseActivity.this, showText,
				Toast.LENGTH_SHORT).show();

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
