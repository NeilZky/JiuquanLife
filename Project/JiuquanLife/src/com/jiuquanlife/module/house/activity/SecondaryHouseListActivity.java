package com.jiuquanlife.module.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.ActionRelationConstance;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.house.adapter.SecondaryHouseAdapter;
import com.jiuquanlife.utils.GsonUtils;
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
	private TextView tv_title_house_list;
	private ListView houseListLv;
	private PopupButton pb_address_ahl;// 区域
	private PopupButton pb_price_ahl;// 价格
	private PopupButton pb_layout_ahl;// 户型
	private PopupButton pb_area_ahl;// 面积
	private PopupButton pb_from_ahl;//来源
	private PopupAdapter<AddressRange> addressAdapter;
	private PopupAdapter<AddressRange> subAddressAdapter;
	public PopupAdapter<PriceRange> priceRangeAdapter;
	public PopupAdapter<LayoutRange> layoutRangeAdapter;
	public PopupAdapter<AreaRange> areaRangeAdapter;
	public PopupAdapter<FromType> fromTypeAdapter;
	private boolean initedMenu;
	private String url;
	private String actionRelation;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
		
	}

	private void init() {
		initView();
		initActionType();
		getData();
	}

	private void initView() {

		setContentView(R.layout.activity_house_list);
		houseListLv = (ListView) findViewById(R.id.lv_seconary_house);
		adapter = new SecondaryHouseAdapter(this);
		houseListLv.setAdapter(adapter);
		houseListLv.setOnItemClickListener(onItemClickListener);
		tv_title_house_list = (TextView) findViewById(R.id.tv_title_house_list);
		initAddrressPopMenu();
		initPricePopMenu();
		initAreaPopMenu();
		initLayoutPopMenu();
		initFromPopMenu();
		initByActionRelation();
	}

	private void initByActionRelation() {
		
		actionRelation = getIntent().getStringExtra(EXTRA_ACTION_RELATION);
		if(ActionRelationConstance.RENT.equals(actionRelation)) {
			url = UrlConstance.GET_RENT_HOUSE_LIST;
			pb_layout_ahl.setVisibility(View.GONE);
			pb_area_ahl.setVisibility(View.GONE);
			tv_title_house_list.setText("出租");
		} else if(ActionRelationConstance.SELL.equals(actionRelation)) {
			url = UrlConstance.GET_SELL_HOUSE_LIST;
			tv_title_house_list.setText("出售");
		} else if(ActionRelationConstance.APPLY_RENT.equals(actionRelation)) {
			url = UrlConstance.GET_APPLY_RENT_HOUSE_LIST;
			pb_layout_ahl.setVisibility(View.GONE);
			pb_area_ahl.setVisibility(View.GONE);
			tv_title_house_list.setText("求租");
		} else if(ActionRelationConstance.APPLY_BUY.equals(actionRelation)) {
			url = UrlConstance.GET_APPLY_BUY_HOUSE_LIST;
			tv_title_house_list.setText("求购");
		}
	}
	
	private void initAddrressPopMenu() {

		pb_address_ahl = (PopupButton) findViewById(R.id.pb_address_ahl);
		addressAdapter = new PopupAdapter<AddressRange>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press2);
		subAddressAdapter = new PopupAdapter<AddressRange>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press);
		View popView = getLayoutInflater().inflate(R.layout.popup2, null);
		ListView pLv = (ListView) popView.findViewById(R.id.parent_lv);
		final ListView cLv = (ListView) popView.findViewById(R.id.child_lv);
		pLv.setAdapter(addressAdapter);
		cLv.setAdapter(subAddressAdapter);
		pb_address_ahl.setPopupView(popView);
		pLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                addressAdapter.setPressPostion(position);
	                addressAdapter.notifyDataSetChanged();
	                AddressRange ar = addressAdapter.getItem(position);
	                if(ar!=null) {
		                subAddressAdapter.refresh(ar.subAddressList);
	                } else {
		                subAddressAdapter.refresh(null);
	                }
	                subAddressAdapter.notifyDataSetChanged();
	                subAddressAdapter.setPressPostion(-1);
	                cLv.setSelection(0);
	            }
	        });

	        cLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            	subAddressAdapter.setPressPostion(position);
	                subAddressAdapter.notifyDataSetChanged();
	                pb_address_ahl.setText(subAddressAdapter.getItem(position).toString());
	                pb_address_ahl.hidePopup(); 
	                getData();
	            }
	        });
	}
	
	
	
	private void initAreaPopMenu() {

		pb_area_ahl = (PopupButton) findViewById(R.id.pb_area_ahl);
		areaRangeAdapter = new PopupAdapter<AreaRange>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press);
		initPopMenu(pb_area_ahl, areaRangeAdapter);
	}
	
	private void initPricePopMenu() {

		pb_price_ahl = (PopupButton) findViewById(R.id.pb_price_ahl);
		priceRangeAdapter = new PopupAdapter<PriceRange>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press);
		initPopMenu(pb_price_ahl, priceRangeAdapter);
	}
	

	private void initLayoutPopMenu() {

		pb_layout_ahl = (PopupButton) findViewById(R.id.pb_layout_ahl);
		layoutRangeAdapter = new PopupAdapter<LayoutRange>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press);
		initPopMenu(pb_layout_ahl, layoutRangeAdapter);
	}
	
	private void initFromPopMenu() {

		pb_from_ahl = (PopupButton) findViewById(R.id.pb_from_ahl);
		fromTypeAdapter = new PopupAdapter<FromType>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press);
		initPopMenu(pb_from_ahl, fromTypeAdapter);
	}
	
	
	private void initPopMenu(final PopupButton pb,final PopupAdapter<?> pbAdapter) {
		
		View areaView = getLayoutInflater().inflate(R.layout.popup, null);
		ListView areaLv = (ListView) areaView.findViewById(R.id.popup_lv);
		areaLv.setAdapter(pbAdapter);
		pb.setPopupView(areaView);
		areaLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pbAdapter.setPressPostion(position);
				pbAdapter.notifyDataSetChanged();
				pb.setText(pbAdapter.getItem(position).toString());
				pb.hidePopup();
				getData();
			}
		});
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
		out.actionRelation = actionRelation;
		out.actionType = actionType;
		if(subAddressAdapter.getSelectedItem()!=null) {
			out.address = subAddressAdapter.getSelectedItem().aid;
		}
		if(priceRangeAdapter.getSelectedItem()!=null) {
			out.price = priceRangeAdapter.getSelectedItem().id;
		}
		if(layoutRangeAdapter.getSelectedItem()!=null) {
			out.layout = layoutRangeAdapter.getSelectedItem().id;
		}
		if(areaRangeAdapter.getSelectedItem()!=null) {
			out.area = areaRangeAdapter.getSelectedItem().id;
		}
		if(fromTypeAdapter.getSelectedItem()!=null) {
			out.from = fromTypeAdapter.getSelectedItem().id;
		}
		RequestHelper.getInstance().getRequestEntity(
				SecondaryHouseListActivity.this,
				url, out,
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
						if(!initedMenu) {
							initMenu(info.data);
							initedMenu = true;
						}
						adapter.refresh(info.data.houseList);
					}
				});
	}

	private void initMenu(GetSellHouseListData data) {

		if (data == null) {
			return;
		}
		addressAdapter.refresh(data.addressList);
		if(data.addressList!=null && !data.addressList.isEmpty()) {
			addressAdapter.setPressPostion(0);
			subAddressAdapter.refresh(data.addressList.get(0).subAddressList);
		}
		areaRangeAdapter.refresh(data.areaRangeList);
		priceRangeAdapter.refresh(data.priceRangeList);
		layoutRangeAdapter.refresh(data.houseLayoutRangeList);
		fromTypeAdapter.refresh(data.fromTypeList);
	}
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_back:
			onClickBack();
			break;
		default:
			break;
		}
		
	}

	private void onClickBack() {
		
		finish();
	}

}
