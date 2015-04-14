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

public class SelectAreaActivity extends BaseActivity {

	public static final String INTENT_KEY_ADDRESS_LIST = "INTENT_KEY_ADDRESS_LIST";
	private static final int REQUEST_SUB_AREA = 1;

	private ListView lv_activity_select_area;
	private AreaAdapter areaAdapter;

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

		ArrayList<AddressRange> ars = (ArrayList<AddressRange>) getIntent()
				.getSerializableExtra(INTENT_KEY_ADDRESS_LIST);
		areaAdapter.refresh(ars);
	}
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			AddressRange ar = areaAdapter.getItem(position);
			Intent intent  = new Intent(getActivity(), SelectSubAreaActivity.class);
			intent.putExtra(SelectSubAreaActivity.INTENT_KEY_ADDRESS_ITEM, ar);
			startActivityForResult(intent, REQUEST_SUB_AREA);
			
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode!=Activity.RESULT_OK) {
			return;
		}
		
		switch (requestCode) {
		case REQUEST_SUB_AREA:
			onResultRequestSubArea(data);
			break;

		default:
			break;
		}
		
	}

	private void onResultRequestSubArea(Intent data) {
		
		setResult(Activity.RESULT_OK, data);
		finish();
	};
}
