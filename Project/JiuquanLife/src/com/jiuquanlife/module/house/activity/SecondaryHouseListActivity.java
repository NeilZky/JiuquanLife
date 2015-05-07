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
import com.jiuquanlife.view.popuplist.PopupAdapter;
import com.jiuquanlife.view.popuplist.PopupButton;
import com.jiuquanlife.vo.house.AddressRange;
import com.jiuquanlife.vo.house.AreaRange;
import com.jiuquanlife.vo.house.FromType;
import com.jiuquanlife.vo.house.GetSellHouseListData;
import com.jiuquanlife.vo.house.GetSellHouseListInfo;
import com.jiuquanlife.vo.house.HouseItem;
import com.jiuquanlife.vo.house.LayoutRange;
import com.jiuquanlife.vo.house.PriceRange;
import com.jiuquanlife.vo.house.out.GetSellerHoustListOut;

public class SecondaryHouseListActivity extends BaseHouseListActivity {

	private SecondaryHouseAdapter adapter;
	private ListView houseListLv;
	private PopupButton pb_area_ahl;// 区域
	private PopupAdapter<AddressRange> areaAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		initView();
		// initVaule();
		// initListener();
		initActionType();
		getData();
	}

	private void initView() {

		setContentView(R.layout.activity_house_list);
		houseListLv = (ListView) findViewById(R.id.lv_seconary_house);
		adapter = new SecondaryHouseAdapter(this);
		houseListLv.setAdapter(adapter);
		houseListLv.setOnItemClickListener(onItemClickListener);
		initAreaPopMenu();
		// priceTab = new ViewLeft(this);
		// viewRight = new ViewRight(this);
	}

	private void initAreaPopMenu() {

		pb_area_ahl = (PopupButton) findViewById(R.id.pb_area_ahl);
		View areaView = getLayoutInflater().inflate(R.layout.popup, null);
		ListView areaLv = (ListView) areaView.findViewById(R.id.popup_lv);
		areaAdapter = new PopupAdapter<AddressRange>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press);
		areaLv.setAdapter(areaAdapter);
		pb_area_ahl.setPopupView(areaView);
		pb_area_ahl.setText("区域");
		areaLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				areaAdapter.setPressPostion(position);
				areaAdapter.notifyDataSetChanged();
				pb_area_ahl.setText(areaAdapter.getItem(position).toString());
				pb_area_ahl.hidePopup();
			}
		});
	}

	private void initVaule() {

	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			HouseItem houseItem = adapter.getItem(position);
			Intent intent = new Intent(SecondaryHouseListActivity.this,
					SellerHouseDetailActivity.class);
			intent.putExtra(SellerHouseDetailActivity.INTENT_KEY_HOUSE_ID,
					houseItem.houseid);
			startActivity(intent);
		}
	};

	private void getData() {

		GetSellerHoustListOut out = new GetSellerHoustListOut();
		out.actionRelation = "2";
		out.actionType = actionType;
		RequestHelper.getInstance().getRequestEntity(
				SecondaryHouseListActivity.this,
				"http://www.5ijq.cn/App/House/getSellHouseList", out,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

						System.out.println(response);
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

		if (data == null) {
			return;
		}
		areaAdapter.refresh(data.addressList);
		// for (AddressRange temp : data.addressList) {
		//
		// label.add(temp.addressName);
		// }
		// addressTab = new ViewLeft(this, label, data.addressList);
		// mTextArray.add("区域");
		// mViewArray.add(addressTab);
	}

	// if ( data.priceRangeList != null
	// && !data.priceRangeList.isEmpty()) {
	// ArrayList<String> label = new ArrayList<String>();
	// for (PriceRange temp : data.priceRangeList) {
	// label.add(temp.priceRange);
	// }
	// priceTab = new ViewLeft(this, label, data.priceRangeList);
	// mTextArray.add("总价");
	// mViewArray.add(priceTab);
	// }
	//
	// if ( data.houseLayoutRangeList != null
	// && !data.houseLayoutRangeList.isEmpty()) {
	// ArrayList<String> label = new ArrayList<String>();
	// for (LayoutRange temp : data.houseLayoutRangeList) {
	// label.add(temp.layoutRange);
	// }
	// layoutTab = new ViewLeft(this, label, data.houseLayoutRangeList);
	// mTextArray.add("厅室");
	// mViewArray.add(layoutTab);
	// }
	//
	// if ( data.areaRangeList != null
	// && !data.areaRangeList.isEmpty()) {
	// ArrayList<String> label = new ArrayList<String>();
	// for (AreaRange temp : data.areaRangeList) {
	// label.add(temp.areaRange);
	// }
	// areaTab = new ViewLeft(this, label, data.areaRangeList);
	// mTextArray.add("面积");
	// mViewArray.add(areaTab);
	// }
	//
	// if ( data.fromTypeList != null
	// && !data.fromTypeList.isEmpty()) {
	// ArrayList<String> label = new ArrayList<String>();
	// for (FromType temp : data.fromTypeList) {
	// label.add(temp.fromType);
	// }
	// fromTypeTab = new ViewLeft(this, label, data.fromTypeList);
	// mTextArray.add("来源");
	// mViewArray.add(fromTypeTab);
	// }
	//
	// expandTabView.setValue(mTextArray, mViewArray);

}
