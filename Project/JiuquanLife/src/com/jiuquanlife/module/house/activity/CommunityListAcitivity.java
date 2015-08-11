package com.jiuquanlife.module.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.CommunityWithPhotoAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.popuplist.PopupAdapter;
import com.jiuquanlife.view.popuplist.PopupButton;
import com.jiuquanlife.vo.house.AddressRange;
import com.jiuquanlife.vo.house.Community;
import com.jiuquanlife.vo.house.CommunityData;
import com.jiuquanlife.vo.house.CommunityListInfo;
import com.jiuquanlife.vo.house.out.GetCommunity;

public class CommunityListAcitivity extends BaseActivity {
	private CommunityWithPhotoAdapter adapter;
	private ListView houseListLv;
	private PopupAdapter<AddressRange> addressAdapter;
	private PopupAdapter<AddressRange> subAddressAdapter;
	private PopupButton pb_address_aal;// 区域
	private boolean initedMenu = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		initView();
		initAddrressPopMenu();
		getData();
	}

	private void initAddrressPopMenu() {

		pb_address_aal = (PopupButton) findViewById(R.id.pb_address_aal);
		addressAdapter = new PopupAdapter<AddressRange>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press2);
		subAddressAdapter = new PopupAdapter<AddressRange>(getActivity(),
				R.layout.popup_item, R.drawable.normal, R.drawable.press);
		View popView = getLayoutInflater().inflate(R.layout.popup2, null);
		ListView pLv = (ListView) popView.findViewById(R.id.parent_lv);
		final ListView cLv = (ListView) popView.findViewById(R.id.child_lv);
		pLv.setAdapter(addressAdapter);
		cLv.setAdapter(subAddressAdapter);
		pb_address_aal.setPopupView(popView);
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
	                pb_address_aal.setText(addressAdapter.getSelectedItem().toString()+ "-" +subAddressAdapter.getItem(position).toString());
	                pb_address_aal.hidePopup(); 
	                getData();
	            }
	        });
	}
	
	
	private void initView() {

		setContentView(R.layout.activity_community_list);
		houseListLv = (ListView) findViewById(R.id.lv_agent_list);
		adapter = new CommunityWithPhotoAdapter(this);
		houseListLv.setAdapter(adapter);
		houseListLv.setOnItemClickListener(onItemClickListener);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Community community = adapter.getItem(position);
			
			Intent intent = new Intent(CommunityListAcitivity.this, CommunityActivity.class);
			intent.putExtra(CommunityActivity.EXTRA_COMMUNITY, community);
			startActivity(intent);
		}
	};
	
	private void getData() {
		
		GetCommunity getCommunity = new GetCommunity();
		AddressRange ar = subAddressAdapter.getSelectedItem();
		if(ar!=null) {
			getCommunity.address = ar.aid;
		} else {
			getCommunity.address = "-1";
		}
		RequestHelper.getInstance().postRequestEntity(CommunityListAcitivity.this,
				UrlConstance.COMMUNITY_LIST, getCommunity,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

						CommunityListInfo info = GsonUtils.toObj(response,
								CommunityListInfo.class);
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
						adapter.refresh(info.data.communityList);
					}
				});
	}

	private void initMenu(CommunityData data) {
		
		addressAdapter.refresh(data.addressList);
		if(data.addressList!=null && !data.addressList.isEmpty()) {
			addressAdapter.setPressPostion(0);
			subAddressAdapter.refresh(data.addressList.get(0).subAddressList);
		}
	}

}
