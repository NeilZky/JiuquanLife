package com.jiuquanlife.module.house.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jiuquanlife.R;
import com.jiuquanlife.constance.ExtraConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.house.activity.HouseListActivity;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.SharePreferenceUtils;

public class HouseMineFragment extends BaseFragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_house_mine, null);
		setContent(content);
		init();
		return content;
	}

	private void init() {

		initViews();
	}

	private void initViews() {

		initClickListener(R.id.ll_house_rent_mine, this);
		initClickListener(R.id.ll_house_sell_mine, this);
		initClickListener(R.id.ll_house_apply_rent_mine, this);
		initClickListener(R.id.ll_house_apply_buy_mine, this);
	}

	@Override
	public void onClick(View v) {
		
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		if(user == null) {
			startActivity(LoginActivity.class);
			return;
		}
		
		Intent intent = new Intent(getActivity(), HouseListActivity.class);
		//1,出租;2,出售;3,求租;4,求购
		String actionRelation = null;
		switch (v.getId()) {
		case R.id.ll_house_rent_mine:
			actionRelation = "1";
			break;
		case R.id.ll_house_sell_mine:
			actionRelation = "2";
			break;
		case R.id.ll_house_apply_rent_mine:
			actionRelation = "3";
			break;
		case R.id.ll_house_apply_buy_mine:
			actionRelation = "4";
			break;
		default:
			break;
		}
		intent.putExtra(ExtraConstance.ActionRelation, actionRelation);
		startActivity(intent);
	}

}
