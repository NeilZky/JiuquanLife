package com.jiuquanlife.module.house.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.AreaAdapter;
import com.jiuquanlife.vo.house.AddressRange;

public class SelectSubAreaActivity extends BaseActivity {

	public static final String INTENT_KEY_ADDRESS_ITEM = "INTENT_KEY_ADDRESS_ITEM";
	public static final String RESULT_DATA_ADDRESS_RANGE = "RESULT_DATA_ADDRESS_RANGE";
	public static final String RESULT_DATA_FATHER_ADDRESS_RANGE = "RESULT_DATA_FATHER_ADDRESS_RANGE";

	private ListView lv_activity_select_area;
	private AreaAdapter areaAdapter;
	private AddressRange fatherAr; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		initViews();
		initData();
	}

	private void initViews() {

		setContentView(R.layout.activity_select_area);
		lv_activity_select_area = (ListView) findViewById(R.id.lv_activity_select_area);
		areaAdapter = new AreaAdapter(this);
		lv_activity_select_area.setAdapter(areaAdapter);
		lv_activity_select_area.setOnItemClickListener(onItemClickListener);
	}

	private void initData() {

		fatherAr = (AddressRange) getIntent()
				.getSerializableExtra(INTENT_KEY_ADDRESS_ITEM);
		ArrayList<AddressRange> ars = new ArrayList<AddressRange>();
		if(fatherAr.subAddressList!=null) {
			ars.addAll(fatherAr.subAddressList);
		}
		areaAdapter.refresh(ars);
	}
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			AddressRange ar = areaAdapter.getItem(position);
			Intent data = new Intent();
			data.putExtra(RESULT_DATA_ADDRESS_RANGE, ar);
			data.putExtra(RESULT_DATA_FATHER_ADDRESS_RANGE, fatherAr);
			setResult(Activity.RESULT_OK, data);
			finish();
		}
	};

}
