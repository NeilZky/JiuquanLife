package com.jiuquanlife.module.love;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.love.entity.FilterDatas;
import com.jiuquanlife.module.love.entity.FilterInfo;

public class SearchFilterActivity extends Activity {
	private TextView tv_cancel, tv_submit;
	private TextView tv_all, tv_male, tv_female;
	private Spinner sp_age, sp_edu, sp_height, sp_location;
	private List<FilterInfo> addressList;
	private List<FilterInfo> ageList;
	private List<FilterInfo> statureList;
	private List<FilterInfo> eduList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_filter);

		addressList = SearchPeopleActivity.filterDatas.getAddressList();
		ageList = SearchPeopleActivity.filterDatas.getAgeList();
		statureList = SearchPeopleActivity.filterDatas.getStatureList();
		eduList = SearchPeopleActivity.filterDatas.getEduList();

		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener(onClickListener);
		tv_submit = (TextView) findViewById(R.id.tv_submit);
		tv_submit.setOnClickListener(onClickListener);

		tv_all = (TextView) findViewById(R.id.tv_genderall);
		tv_all.setOnClickListener(onClickListener);
		tv_male = (TextView) findViewById(R.id.tv_male);
		tv_male.setOnClickListener(onClickListener);
		tv_female = (TextView) findViewById(R.id.tv_female);
		tv_female.setOnClickListener(onClickListener);

		sp_age = (Spinner) findViewById(R.id.sp_age);

		final ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				SearchPeopleActivity.filterDatas.ageArray());
		// 绑定 Adapter到控件
		sp_age.setAdapter(_Adapter);

		sp_age.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String value = _Adapter.getItem(position);
				handleSelectAge(position, value);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		if (null == SearchPeopleActivity.filterDatas) {
			System.out.println("is null");
		}

		sp_edu = (Spinner) findViewById(R.id.sp_edu);

		final ArrayAdapter<String> _Adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				SearchPeopleActivity.filterDatas.eduArray());
		// 绑定 Adapter到控件
		sp_edu.setAdapter(_Adapter1);

		sp_edu.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String value = _Adapter1.getItem(position);
				handleSelectEducation(position, value);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sp_height = (Spinner) findViewById(R.id.sp_height);

		final ArrayAdapter<String> _Adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				SearchPeopleActivity.filterDatas.statureArray());
		// 绑定 Adapter到控件
		sp_height.setAdapter(_Adapter2);
		sp_height.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String value = _Adapter2.getItem(position);
				handleSelectHeight(position, value);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sp_location = (Spinner) findViewById(R.id.sp_location);

		final ArrayAdapter<String> _Adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				SearchPeopleActivity.filterDatas.addressArray());
		// 绑定 Adapter到控件
		sp_location.setAdapter(_Adapter3);
		sp_location.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String value = _Adapter3.getItem(position);
				handleSelectLocation(position, value);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	String education = "";
	String location = "";
	String beginAge = "0";
	String endAge = "100";
	String beginHeight = "100";
	String endHeight = "250";

	private void handleSelectLocation(int position, String value) {
		location = addressList.get(position).getId();
	}

	private void handleSelectEducation(int position, String value) {
		education = eduList.get(position).getId();
	}

	private void handleSelectAge(int position, String value) {
		beginAge = ageList.get(position).getId();
		System.out.println("beginAge:" + beginAge + " end age:" + endAge);
	}

	private void handleSelectHeight(int position, String value) {
		beginHeight = statureList.get(position).getId();

		System.out.println("beginHeight:" + beginHeight + " end height:"
				+ endHeight);
	}

	private String gender = "";

	private void handleClick(int status) {
		switch (status) {
		case 1:
			gender = "0";
			tv_all.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_pressed));
			tv_male.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_normal));
			tv_female.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_normal));

			break;
		case 2:
			tv_all.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_normal));
			tv_male.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_pressed));
			tv_female.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_normal));
			gender = "1";
			break;
		case 3:
			tv_all.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_normal));
			tv_male.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_normal));
			tv_female.setBackground(getResources().getDrawable(
					R.drawable.filter_bg_pressed));
			gender = "2";
			break;
		default:
			break;
		}
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_cancel:
				finish();
				break;
			case R.id.tv_genderall:
				handleClick(1);
				break;
			case R.id.tv_female:
				handleClick(3);
				break;
			case R.id.tv_male:
				handleClick(2);
				break;
			case R.id.tv_submit:
				handleSubmit();
				break;
			default:
				break;
			}

		}
	};

	private void handleSubmit() {
		Intent it = new Intent(SearchFilterActivity.this,
				SearchPeopleActivity.class);

		it.putExtra("gender", gender);
		it.putExtra("beginAge", beginAge);
		it.putExtra("endAge", endAge);
		it.putExtra("beginHeight", beginHeight);
		it.putExtra("endHeight", endHeight);
		it.putExtra("edu", education);
		it.putExtra("location", location);
		setResult(RESULT_OK, it);
		finish();
	}

}
