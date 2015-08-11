package com.jiuquanlife.module.house.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.SampleImagePagerAdapter;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.SecondaryHouseAdapter;
import com.jiuquanlife.module.house.adapter.TagAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.view.HorizontalListView;
import com.jiuquanlife.view.UnScrollListView;
import com.jiuquanlife.vo.house.Agent;
import com.jiuquanlife.vo.house.AgentDetail;
import com.jiuquanlife.vo.house.Community;
import com.jiuquanlife.vo.house.AgentDetail;
import com.jiuquanlife.vo.house.AgentDetailInfo;
import com.jiuquanlife.vo.house.HouseItem;
import com.jiuquanlife.vo.house.Img;
import com.photoselector.ui.PhotoItem.onItemClickListener;

public class AgentActivity extends BaseActivity {

	public static final String EXTRA_AGENT = "EXTRA_AGENT";
	private Agent agent;
	
	private ViewPager photoVp;
	private SampleImagePagerAdapter photoAdapter;
	private TagAdapter tagAdapter;
	
	private TextView tv_title_community_detail;
	private TextView tv_name_community;
	private TextView tv_area_community;
	private TextView tv_address_community;
	private TextView tv_make_company_commnity;
	private TextView tv_people_num_community;
	private TextView tv_quality_community;
	private TextView tv_unit_num_community;
	private TextView tv_make_year_community;
	private TextView tv_price_community;
	private HorizontalListView hlv_tag_community;
	private TextView tv_locaton_community;
	private UnScrollListView uslv_community;
	private SecondaryHouseAdapter houseAdapter;
	private View v_divider_chuzu;
	private View v_divider_ershou;
	private TextView tv_watch_more_community;

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
		setContentView(R.layout.activity_community_detail);
		photoVp = (ViewPager) findViewById(R.id.vp_acd);
		photoAdapter = new SampleImagePagerAdapter(getActivity(), photoVp);
		agent = (Agent) getIntent().getSerializableExtra(
				EXTRA_AGENT);
		tv_title_community_detail = (TextView) findViewById(R.id.tv_title_community_detail);
		tv_name_community = (TextView) findViewById(R.id.tv_name_community);
		tv_area_community = (TextView) findViewById(R.id.tv_area_community);
		tv_address_community = (TextView) findViewById(R.id.tv_address_community);
		tv_make_company_commnity = (TextView) findViewById(R.id.tv_make_company_commnity);
		tv_people_num_community = (TextView) findViewById(R.id.tv_people_num_community);
		tv_quality_community = (TextView) findViewById(R.id.tv_quality_community);
		tv_unit_num_community = (TextView) findViewById(R.id.tv_unit_num_community);
		tv_make_year_community = (TextView) findViewById(R.id.tv_make_year_community);
		tv_price_community = (TextView) findViewById(R.id.tv_price_community);
		tv_locaton_community = (TextView) findViewById(R.id.tv_locaton_community);
		tv_watch_more_community = (TextView) findViewById(R.id.tv_watch_more_community);
//		TextViewUtils.setText(tv_title_community_detail, agent.);
//		TextViewUtils.setText(tv_name_community, agent.communityName);
		hlv_tag_community = (HorizontalListView) findViewById(R.id.hlv_tag_community);
		tagAdapter = new TagAdapter(getActivity());
		hlv_tag_community.setAdapter(tagAdapter);
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
//		TextViewUtils.setText(tv_area_community, agentDetail.buildsquare);
//		TextViewUtils.setText(tv_address_community, agentDetail.locationName + "-" +agentDetail.partLocationName);
//		TextViewUtils.setText(tv_locaton_community, agentDetail.locationName + "-" +agentDetail.partLocationName);
//		TextViewUtils.setText(tv_make_company_commnity, agentDetail.makeCompany);
//		TextViewUtils.setText(tv_people_num_community, agentDetail.peopleNum);
//		TextViewUtils.setText(tv_quality_community, agentDetail.communityQuality);
//		TextViewUtils.setText(tv_unit_num_community, agentDetail.unitNum);
//		TextViewUtils.setText(tv_make_year_community, agentDetail.makeYear);
//		TextViewUtils.setText(tv_price_community, agentDetail.propertyPrice);
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
