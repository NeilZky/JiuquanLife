package com.jiuquanlife.module.house.activity;

import com.jiuquanlife.module.base.BaseActivity;

public class BaseHouseListActivity extends BaseActivity{
	protected String actionType;
	public static final String EXTRA_ACTION_TYPE = "EXTRA_ACTION_TYPE";
	public static final String EXTRA_ACTION_RELATION = "EXTRA_ACTION_RELATION";

	protected void initActionType() {
		
		String btnText = getIntent().getStringExtra(EXTRA_ACTION_TYPE);
		if("二手房".equals(btnText)) {
			actionType = "1";
		} else if("整套".equals(btnText)) {
			actionType = "2";
		} else if("单间".equals(btnText)) {
			actionType = "3";
		} else if("床位".equals(btnText)) {
			actionType = "4";
		} else if("商铺".equals(btnText)) {
			actionType = "5";
		} else if("厂房/仓库/土地/车位".equals(btnText)) {
			actionType = "6";
		} 
	}
}
