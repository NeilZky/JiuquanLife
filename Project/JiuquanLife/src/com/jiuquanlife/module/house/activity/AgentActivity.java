package com.jiuquanlife.module.house.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.SampleImagePagerAdapter;
import com.jiuquanlife.app.App;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.SecondaryHouseAdapter;
import com.jiuquanlife.module.house.adapter.TagAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.view.HorizontalListView;
import com.jiuquanlife.view.UnScrollListView;
import com.jiuquanlife.vo.house.Agent;
import com.jiuquanlife.vo.house.AgentDetail;
import com.jiuquanlife.vo.house.AgentDetailInfo;
import com.jiuquanlife.vo.house.HouseItem;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AgentActivity extends BaseActivity {

	public static final String EXTRA_AGENT = "EXTRA_AGENT";
	private Agent agent;
	
	
	private UnScrollListView uslv_community;
	private SecondaryHouseAdapter houseAdapter;
	private View v_divider_chuzu;
	private View v_divider_ershou;
	private TextView tv_watch_more_community;

	private ImageView iv_agent_user;
	private TextView tv_agent_name_detail;
	private TextView tv_company_name_detail;
	private TextView tv_recent_info_detail;
	private TextView tv_familar_community_agent;
	private TextView tv_address_agent;
	private TextView tv_good_at_agent;
	
	
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
		
		setContentView(R.layout.activity_agent_detail);
		iv_agent_user = (ImageView) findViewById(R.id.iv_agent_user);
		tv_agent_name_detail = (TextView) findViewById(R.id.tv_agent_name_detail);
		tv_company_name_detail = (TextView) findViewById(R.id.tv_company_name_detail);
		tv_recent_info_detail = (TextView) findViewById(R.id.tv_recent_info_detail);
		tv_familar_community_agent = (TextView) findViewById(R.id.tv_familar_community_agent);
		tv_address_agent = (TextView) findViewById(R.id.tv_address_agent);
		tv_good_at_agent = (TextView) findViewById(R.id.tv_good_at_agent);
		agent = (Agent) getIntent().getSerializableExtra(
				EXTRA_AGENT);
		
		
		tv_watch_more_community = (TextView) findViewById(R.id.tv_watch_more_community);
//		imageLoader.displayImage(url, holder.img, App.getOptions());
		TextViewUtils.setText(tv_agent_name_detail, agent.trueName);
		uslv_community = (UnScrollListView) findViewById(R.id.uslv_community);
		houseAdapter = new SecondaryHouseAdapter(getActivity());
		uslv_community.setAdapter(houseAdapter);
		uslv_community.setOnItemClickListener(onItemClickListener);
		v_divider_chuzu = findViewById(R.id.v_divider_chuzu);
		v_divider_ershou = findViewById(R.id.v_divider_ershou);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			HouseItem item = (HouseItem) parent.getItemAtPosition(position);
			Intent intent = new Intent(getActivity(),
					SellerHouseDetailActivity.class);
			//TODO
//			intent.putExtra(SellerHouseDetailActivity.EXTRA_ACTION_TYPE, item.actionRelation);
			intent.putExtra(SellerHouseDetailActivity.EXTRA_ACTION_RELATION, item.actionRelation);
			intent.putExtra(SellerHouseDetailActivity.INTENT_KEY_HOUSE_ID,
					item.houseid);
			startActivity(intent);
		}
	};
	
	private void getData() {

		String url = UrlConstance.AGENT_DETAIL + agent.agid;
		RequestHelper.getInstance().getRequest(getActivity(), url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

						AgentDetailInfo info = GsonUtils.toObj(response,
								AgentDetailInfo.class);
						if(info!=null && info.data!=null && info.data.agent!=null)  {
							fillViews(info.data.agent);
						}
					}
				
				});
	}

	private AgentDetail agentDetail;
	
	private void fillViews(AgentDetail agentDetail) {
		
		this.agentDetail = agentDetail;
		TextViewUtils.setText(tv_agent_name_detail, agentDetail.trueName);
		TextViewUtils.setText(tv_company_name_detail, agentDetail.compName);
		if(TextUtils.isEmpty(agentDetail.viewNum)) {
			agentDetail.viewNum = "0";
		}
		if(TextUtils.isEmpty(agentDetail.teachNum)) {
			agentDetail.teachNum = "0";
		}
		String recentInfo = "从业年限: " + agentDetail.workAge + " 年   " + "近30天带看: " + agent.teachNum +"次 点击次数  " + 
		agentDetail.viewNum + "次 ";
		TextViewUtils.setText(tv_recent_info_detail, recentInfo);
		TextViewUtils.setText(tv_familar_community_agent, agentDetail.serveLocation);
		TextViewUtils.setText(tv_good_at_agent, agentDetail.shopServe);
		TextViewUtils.setText(tv_address_agent, agentDetail.locationName +agentDetail.partLocationName + agentDetail.shopAddr);
		onClickErshou();
	}
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_chuzu_community:
			onClickChuzu();
			break;
		case R.id.ll_ershou_community:
			onClickErshou();
			break;
		case R.id.tv_watch_more_community:
			onClickMore(v);
			break;
		default:
			break;
		}
		
	}

	private void onClickMore(View v) {
		
		v.setVisibility(View.GONE);
		if(v_divider_chuzu.getVisibility() == View.VISIBLE) {
			onClickChuzu();
		} else {
			onClickErshou();
		}
	}

	private ArrayList<HouseItem> getTopThree(ArrayList<HouseItem> list) {
		
		if(list!=null && list.size() > 3) {
			 ArrayList<HouseItem>  res = new ArrayList<HouseItem>();
			 for(int i =0 ; i < 3 ; i++) {
				 res.add(list.get(i));
			 }
			 return res;
		}
		return list;
	}
	
	private void onClickErshou() {
		
		if(agent!=null) {
			if(tv_watch_more_community.getVisibility() == View.VISIBLE ) {
				houseAdapter.refresh(getTopThree(agentDetail.ershouList));
			} else {
				houseAdapter.refresh(agentDetail.ershouList);
			}
		}
		v_divider_ershou.setVisibility(View.VISIBLE);
		v_divider_chuzu.setVisibility(View.INVISIBLE);
	}

	private void onClickChuzu() {
		
		if(agent!=null) {
			if(tv_watch_more_community.getVisibility() == View.VISIBLE ) {
				houseAdapter.refresh(getTopThree(agentDetail.chuzuList));
			} else {
				houseAdapter.refresh(agentDetail.chuzuList);
			}
		}
		v_divider_ershou.setVisibility(View.INVISIBLE);
		v_divider_chuzu.setVisibility(View.VISIBLE);
	}
	
	
}
