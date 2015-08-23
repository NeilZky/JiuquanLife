package com.jiuquanlife.module.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.AgentAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.popuplist.PopupAdapter;
import com.jiuquanlife.view.popuplist.PopupButton;
import com.jiuquanlife.vo.house.AddressRange;
import com.jiuquanlife.vo.house.Agent;
import com.jiuquanlife.vo.house.AgentData;
import com.jiuquanlife.vo.house.AgentInfo;
import com.jiuquanlife.vo.house.out.GetAgent;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout.OnRefreshListener;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

public class AgentListAcitivity extends BaseActivity implements OnRefreshListener {

	private AgentAdapter adapter;
	private ListView houseListLv;
	private PopupAdapter<AddressRange> addressAdapter;
	private PopupAdapter<AddressRange> subAddressAdapter;
	private PopupButton pb_address_aal;// ����
	private boolean initedMenu = false;
	private SwipyRefreshLayout srl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		initView();
		initAddrressPopMenu();
		srl.setRefreshing(true);
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
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				addressAdapter.setPressPostion(position);
				addressAdapter.notifyDataSetChanged();
				AddressRange ar = addressAdapter.getItem(position);
				if (ar != null) {
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
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				subAddressAdapter.setPressPostion(position);
				subAddressAdapter.notifyDataSetChanged();
				pb_address_aal.setText(subAddressAdapter.getItem(position)
						.toString());
				pb_address_aal.hidePopup();
				getData();
			}
		});
	}

	private void initView() {

		setContentView(R.layout.activity_agent_list);
		houseListLv = (ListView) findViewById(R.id.lv_agent_list);
		adapter = new AgentAdapter(this);
		houseListLv.setAdapter(adapter);
		houseListLv.setOnItemClickListener(onItemClickListener);
		srl = (SwipyRefreshLayout) findViewById(R.id.srl_agent_list);
		srl.setDirection(SwipyRefreshLayoutDirection.BOTH);
		srl.setOnRefreshListener(this);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Agent agent = adapter.getItem(position);
			Intent intent = new Intent(AgentListAcitivity.this,
					AgentActivity.class);
			intent.putExtra(AgentActivity.EXTRA_AGENT, agent);
			startActivity(intent);
		}
	};
	
	private int page;
	
	private void getData() {

		GetAgent getAgent = new GetAgent();
		if (subAddressAdapter.getSelectedItem() != null) {
			getAgent.location = subAddressAdapter.getSelectedItem().aid;
		} else {
			getAgent.location = "-1";
		}
		page = 1;
		getAgent.page = page + "";
		RequestHelper.getInstance().getRequestEntity(AgentListAcitivity.this,
				UrlConstance.AGENT_LIST, getAgent, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						srl.setRefreshing(false);
						AgentInfo info = GsonUtils.toObj(response,
								AgentInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// ��������ʧ��
							return;
						}
						if (!initedMenu) {
							initMenu(info.data);
							initedMenu = true;
						}
						adapter.refresh(info.data.agentList);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						srl.setRefreshing(false);
					}
				});
	}
	
	
	private void addData() {

		GetAgent getAgent = new GetAgent();
		if (subAddressAdapter.getSelectedItem() != null) {
			getAgent.location = subAddressAdapter.getSelectedItem().aid;
		} else {
			getAgent.location = "-1";
		}
		page++;
		getAgent.page = page + "";
		RequestHelper.getInstance().getRequestEntity(AgentListAcitivity.this,
				UrlConstance.AGENT_LIST, getAgent, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						srl.setRefreshing(false);
						AgentInfo info = GsonUtils.toObj(response,
								AgentInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// ��������ʧ��
							return;
						}
						adapter.add(info.data.agentList);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						srl.setRefreshing(false);
					}
				});
	}
	
	private void initMenu(AgentData data) {

		addressAdapter.refresh(data.addressList);
		if (data.addressList != null && !data.addressList.isEmpty()) {
			addressAdapter.setPressPostion(0);
			subAddressAdapter.refresh(data.addressList.get(0).subAddressList);
		}

	}
	
	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		
		if(direction == SwipyRefreshLayoutDirection.TOP) {
			getData();
		} else if(direction == SwipyRefreshLayoutDirection.BOTTOM) {
			addData();
		}
	}
}
