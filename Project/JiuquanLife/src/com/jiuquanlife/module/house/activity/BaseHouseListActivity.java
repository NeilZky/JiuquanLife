package com.jiuquanlife.module.house.activity;

import com.jiuquanlife.module.base.BaseActivity;

public class BaseHouseListActivity extends BaseActivity{
	protected String actionType;
	public static final String EXTRA_ACTION_TYPE = "EXTRA_ACTION_TYPE";
	public static final String EXTRA_ACTION_RELATION = "EXTRA_ACTION_RELATION";

	protected void initActionType() {
		
		String btnText = getIntent().getStringExtra(EXTRA_ACTION_TYPE);
		if("���ַ�".equals(btnText)) {
			actionType = "1";
		} else if("����".equals(btnText)) {
			actionType = "2";
		} else if("����".equals(btnText)) {
			actionType = "3";
		} else if("��λ".equals(btnText)) {
			actionType = "4";
		} else if("����".equals(btnText)) {
			actionType = "5";
		} else if("����/�ֿ�/����/��λ".equals(btnText)) {
			actionType = "6";
		} 
	}
}
