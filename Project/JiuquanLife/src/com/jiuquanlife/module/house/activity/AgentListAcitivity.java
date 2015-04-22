package com.jiuquanlife.module.house.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.AgentAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.expand.ExpandTabView;
import com.jiuquanlife.view.expand.ViewLeft;
import com.jiuquanlife.view.expand.ViewRight;
import com.jiuquanlife.vo.house.AddressRange;
import com.jiuquanlife.vo.house.Agent;
import com.jiuquanlife.vo.house.AgentData;
import com.jiuquanlife.vo.house.AgentInfo;
import com.jiuquanlife.vo.house.out.GetAgent;

public class AgentListAcitivity extends BaseActivity {

	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft addressTab;
	private ViewLeft priceTab;
	private ViewLeft layoutTab;
	private ViewLeft areaTab;
	private ViewLeft fromTypeTab;
	private ViewRight viewRight;
	private AgentAdapter adapter;
	private ListView houseListLv;
	private EditText et_search_aal;
	private AddressRange addressRange ;
	private boolean initedMenu;

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

		setContentView(R.layout.activity_agent_list);
		expandTabView = (ExpandTabView) findViewById(R.id.etv_agent_list);
		houseListLv = (ListView) findViewById(R.id.lv_agent_list);
		et_search_aal = (EditText) findViewById(R.id.et_search_aal);
		adapter = new AgentAdapter(this);
		houseListLv.setAdapter(adapter);
		houseListLv.setOnItemClickListener(onItemClickListener);
		// priceTab = new ViewLeft(this);
		// viewRight = new ViewRight(this);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Agent agent = adapter.getItem(position);
			Intent intent = new Intent(AgentListAcitivity.this, AgentDetailActivity.class);
			intent.putExtra(AgentDetailActivity.INTENT_KEY_AGENT_ID, agent.agid);
			startActivity(intent);
		}
	};
	
	private void getData() {
		
		GetAgent	getAgent = new GetAgent();
		if(addressRange!=null) {
			getAgent.location = addressRange.aid;
		} 
		if(!et_search_aal.getText().toString().trim().isEmpty()) {
			getAgent.word = et_search_aal.getText().toString().trim();
		}
		RequestHelper.getInstance().postRequestEntity(AgentListAcitivity.this,
				UrlConstance.AGENT_LIST, getAgent,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

						AgentInfo info = GsonUtils.toObj(response,
								AgentInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// 请求数据失败
							return;
						}
						initMenu(info.data);
						adapter.refresh(info.data.agentList);
					}
				});
	}

	private void initMenu(AgentData data) {
		
		if(data == null || initedMenu){
			return;
		}
		initedMenu = true;
		
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
		
		expandTabView.setValue(mTextArray, mViewArray);
		initListener();

	}

	private void initListener() {

		if (addressTab != null) {
			addressTab.setOnSelectListener(new ViewLeft.OnSelectListener() {

				@Override
				public void getValue(Object obj) {
					addressRange = (AddressRange) obj;
					onRefreshTab(addressTab, addressRange.addressName);
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

	private void onRefreshTab(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		getData();
	}

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_search_aal:
			onClickSearch();
			break;
		default:
			break;
		}
	}
	
	private void onClickSearch() {

		getData();
	}

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}
	}

}
