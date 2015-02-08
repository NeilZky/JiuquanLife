package com.jiuquanlife.module.house.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.focus.adapter.FocusTopAdapter;
import com.jiuquanlife.module.focus.adapter.JhtAdapter;
import com.jiuquanlife.module.post.PostDetailActivity;
import com.jiuquanlife.utils.GsonUtils;
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
			default:
				break;
			}
			
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
