package com.jiuquanlife.module.house.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.focus.adapter.FocusTopAdapter;
import com.jiuquanlife.module.focus.adapter.JhtAdapter;
import com.jiuquanlife.module.house.activity.AgentListAcitivity;
import com.jiuquanlife.module.house.activity.PublishHouseActivity;
import com.jiuquanlife.module.house.activity.RentHouseListActivity;
import com.jiuquanlife.module.house.activity.SecondaryHouseListActivity;
import com.jiuquanlife.module.house.activity.WantedBuyHouseListActivity;
import com.jiuquanlife.module.house.activity.WantedRentHouseListActivity;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.module.post.PostDetailActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.view.UnScrollListView;
import com.jiuquanlife.vo.PhotoInfo;
import com.jiuquanlife.vo.PostInfo;
import com.jiuquanlife.vo.convertor.ConvertUtils;
import com.jiuquanlife.vo.house.HouseInfo;

public class HouseFragment extends BaseFragment{

	
	private ViewPager topVp;
	private FocusTopAdapter focusTopAdapter;
	private LinearLayout dotLl;
	private TextView vpTitleTv;
	private UnScrollListView jhtLv;
	private JhtAdapter jhtAdapter;
	private LinearLayout rentMenuLl;
	private LinearLayout sellMenuLl;
	private LinearLayout applyRentMenuLl;
	private LinearLayout buyMenuLl;
	private View rentView;
	private View sellView;
	private View applyView;
	private View buyView;
	private Button sellSecondaryHouse;
	private Button btn_apply_rent_house;
	private Button btn_rent_house;
	private Button btn_wanted_buy_secondary_house;
	private Button btn_publish_fh;
	private LinearLayout ll_agent_fragment_house;
	
	private static final int REQUEST_LOGIN = 1;
	private static final int REQUEST_PUBLISH = 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View content = inflater.inflate(R.layout.fragment_house, null);
		setContent(content);
		init();
		return content;
	}

	private void init() {

		initViews();
		getData();
	}

	private void initViews() {
		
		rentView = findViewById(R.id.ll_rent_house);
		sellView = findViewById(R.id.ll_sell_house);
		applyView = findViewById(R.id.ll_apply_rent_house);
		buyView = findViewById(R.id.ll_buy_house);
		rentMenuLl = (LinearLayout) findViewById(R.id.ll_menu_rent_house);
		sellMenuLl = (LinearLayout) findViewById(R.id.ll_menu_sell_house);
		applyRentMenuLl = (LinearLayout) findViewById(R.id.ll_menu_apply_rent_house);
		buyMenuLl = (LinearLayout) findViewById(R.id.ll_menu_buy_house);
		topVp = (ViewPager) findViewById(R.id.vp_top_house);
		dotLl = (LinearLayout) findViewById(R.id.ll_dot_top_house);
		vpTitleTv = (TextView) findViewById(R.id.tv_vp_title_house);
		jhtLv = (UnScrollListView) findViewById(R.id.uslv_jht_house);
		btn_apply_rent_house = (Button) findViewById(R.id.btn_apply_rent_house);
		btn_rent_house = (Button) findViewById(R.id.btn_rent_house);
		btn_wanted_buy_secondary_house = (Button) findViewById(R.id.btn_wanted_buy_secondary_house);
		ll_agent_fragment_house = (LinearLayout) findViewById(R.id.ll_agent_fragment_house);
		focusTopAdapter = new FocusTopAdapter(getActivity(), dotLl, topVp,
				vpTitleTv);
		topVp.setOnPageChangeListener(focusTopAdapter);

		jhtAdapter = new JhtAdapter(getActivity());
		jhtLv.setAdapter(jhtAdapter);
		jhtLv.setOnItemClickListener(onItemClickListener);
		focusTopAdapter.setOnClickItemListener(onClickItemListener);
		rentView.setOnClickListener(onClickListener);
		sellView.setOnClickListener(onClickListener);
		applyView.setOnClickListener(onClickListener);
		buyView.setOnClickListener(onClickListener);
		btn_apply_rent_house.setOnClickListener(onClickListener);
		btn_rent_house.setOnClickListener(onClickListener);
		btn_wanted_buy_secondary_house.setOnClickListener(onClickListener);
		ll_agent_fragment_house.setOnClickListener(onClickListener);
		
		sellSecondaryHouse =(Button) findViewById(R.id.btn_sell_secondary_house);
		btn_publish_fh = (Button) findViewById(R.id.btn_publish_fh);
		btn_publish_fh.setOnClickListener(onClickListener);
		sellSecondaryHouse.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.ll_rent_house:
				showSenconaryMenu(rentMenuLl);
				break;
			case R.id.ll_sell_house:
				showSenconaryMenu(sellMenuLl);
				break;
			case R.id.ll_apply_rent_house:
				showSenconaryMenu(applyRentMenuLl);
				break;
			case R.id.ll_buy_house:
				showSenconaryMenu(buyMenuLl);
				break;
			case R.id.btn_sell_secondary_house:
				onClickSellerSecondaryHouse();
				break;
			case R.id.btn_apply_rent_house:
				onClickApplyRentHouse();
				break;
			case R.id.btn_rent_house:
				onClickRentHouse();
				break;
			case R.id.btn_wanted_buy_secondary_house:
				onClickWantedBuySecondary();
				break;
			case R.id.btn_publish_fh:
				onClickPublish();
				break;
			case R.id.ll_agent_fragment_house:
				onClickAgent();
				break;
			default:
				break;
			}
		}
		
		private void onClickPublish() {
			
			User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
			if(user == null) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivityForResult(intent, REQUEST_LOGIN);
			} else {
				startPublishActivity();
			}
		}

		
		
		private void onClickWantedBuySecondary() {
			
			Intent intent = new Intent(getActivity(), WantedBuyHouseListActivity.class);
			startActivity(intent);
		}

		private void onClickRentHouse() {
			
			Intent intent = new Intent(getActivity(), RentHouseListActivity.class);
			startActivity(intent);
		}

		private void onClickApplyRentHouse() {
			
			Intent intent = new Intent(getActivity(), WantedRentHouseListActivity.class);
			startActivity(intent);
		}

		private void onClickSellerSecondaryHouse() {
			
			Intent intent = new Intent(getActivity(), SecondaryHouseListActivity.class);
			startActivity(intent);
		}
	};
	
	private OnClickListener onClickItemListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			PhotoInfo photoInfo = focusTopAdapter.getCurrentItem();
			if (photoInfo != null) {
				Intent intent = new Intent(getActivity(),
						PostDetailActivity.class);
				intent.putExtra(PostDetailActivity.INTENT_KEY_TID,
						photoInfo.tid);
				startActivity(intent);
			}
		}
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode!=Activity.RESULT_OK) {
			return;
		}
		
		switch (requestCode) {
		case REQUEST_LOGIN:
			onResultLogin(data);
			break;
		default:
			break;
		}
	};
	
	private void onResultLogin(Intent data) {
		
		startPublishActivity();
	}

	private void startPublishActivity() {
		
		Intent intent = new Intent(getActivity(), PublishHouseActivity.class);
		startActivityForResult(intent, REQUEST_PUBLISH);
	}
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_rent_house:
			showSenconaryMenu(rentMenuLl);
			break;
		case R.id.ll_sell_house:
			showSenconaryMenu(sellMenuLl);
			break;
		case R.id.ll_apply_rent_house:
			showSenconaryMenu(applyRentMenuLl);
			break;
		case R.id.ll_buy_house:
			showSenconaryMenu(buyMenuLl);
			break;
		default:
			break;
		}
	}
	
	private void onClickAgent() {
		
		Intent intent = new Intent(getActivity(), AgentListAcitivity.class);
		startActivity(intent);
	}

	private void showSenconaryMenu(View view) {
		
		closeOtherMenu(view);
		if(view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
		} else{
			view.setVisibility(View.VISIBLE);
		}
	}
	
	
	
	
	private void closeOtherMenu(View view) {
		
		ViewGroup parent = (ViewGroup)view.getParent();
		for(int i = 0 ; i < parent.getChildCount(); i ++) {
			View child = parent.getChildAt(i);
			if(child != view) {
				child.setVisibility(View.GONE);
			}
		}
	}
	
	
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			switch (parent.getId()) {
			case R.id.uslv_jht_focus:
				onClickJhtItem(position);
				break;
			default:
				break;
			}

		}


		private void onClickJhtItem(int position) {

			PostInfo postInfo = jhtAdapter.getItem(position);
			Intent intent = new Intent(getActivity(), PostDetailActivity.class);
			intent.putExtra(PostDetailActivity.INTENT_KEY_TID, postInfo.tid);
			startActivity(intent);
		}
	};
	

	public void getData() {

		RequestHelper.getInstance().getRequest(getActivity(),
				"http://www.5ijq.cn/App/House",
				new Listener<String>() {

					@Override
					public void onResponse(String response) {

						HouseInfo info = GsonUtils.toObj(response,
								HouseInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// ÇëÇóÊý¾ÝÊ§°Ü
							return;
						}
						ArrayList<PhotoInfo> focusTopPhotoInfos = ConvertUtils
								.convertToPhotoInfos(info.data.HouseImgs);
						focusTopAdapter.setPhotoInfos(focusTopPhotoInfos);
						topVp.setAdapter(focusTopAdapter);
						jhtAdapter.refresh(info.data.HousePost);
					}
				});
	}
	
}
