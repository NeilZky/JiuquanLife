package com.jiuquanlife.module.company.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.adapter.HotSearchAdapter;
import com.jiuquanlife.module.company.adapter.SearchHistoryAdapter;
import com.jiuquanlife.module.company.entity.HotSearch;
import com.jiuquanlife.module.company.util.SharePreferenceHelp;

public class CompanySearchActivity extends Activity {
	private LinearLayout ll_back;
	private GridView gv_hot;
	private ListView lv_history;
	private TextView btn_search;
	private EditText et_search;
	private static final int MESSAGE_INIT_DATA_SUCCESS = 1;
	private static final int MSG_INIT_DATA_FAILED = 2;
	private HotSearch hotSearch;
	private TextView tvClear;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_INIT_DATA_SUCCESS:
				initHotSearch();
				break;
			case MSG_INIT_DATA_FAILED:

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void initHotSearch() {
		System.out.println("initHotSearch");
		List<String> name = (null == hotSearch ? new ArrayList<String>()
				: hotSearch.getData());
		final HotSearchAdapter hotSearchAdapter = new HotSearchAdapter(name,
				CompanySearchActivity.this);
		gv_hot.setAdapter(hotSearchAdapter);
		gv_hot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String search = hotSearchAdapter.getItem(position);
				SharePreferenceHelp.getInstance(getApplicationContext())
						.saveSearchHistory(search,
								SharePreferenceHelp.COMPANY_SEARCH_HISTORY);
				Intent intent = new Intent(CompanySearchActivity.this,
						CategoryDetailActivity.class);
				System.out.println("gv_hot:" + search);
				intent.putExtra("search", search);
				startActivity(intent);
				finish();

			}
		});
		hotSearchAdapter.notifyDataSetChanged();

	}

	private void initSearchHistory() {
		Set<String> searchHistory = SharePreferenceHelp.getInstance(
				getApplicationContext()).getSearchHistory(
				SharePreferenceHelp.COMPANY_SEARCH_HISTORY);
		System.out.println("searchHistory:" + searchHistory);
		final SearchHistoryAdapter searchHistoryAdapter = new SearchHistoryAdapter(
				searchHistory, CompanySearchActivity.this);
		lv_history.setAdapter(searchHistoryAdapter);
		lv_history.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String search = searchHistoryAdapter.getItem(position);
				Intent intent = new Intent(CompanySearchActivity.this,
						CategoryDetailActivity.class);
				intent.putExtra("search", search);
				startActivity(intent);
				finish();
			}
		});
		searchHistoryAdapter.notifyDataSetChanged();
		System.out.println("getCount:" + searchHistoryAdapter.getCount());

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_search);
		initView();
		loadData();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		initSearchHistory();
		super.onResume();
	}

	private void initView() {
		tvClear = (TextView) findViewById(R.id.tv_clear);
		tvClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SharePreferenceHelp.getInstance(getApplicationContext())
						.clearSearchHiostory(
								SharePreferenceHelp.COMPANY_SEARCH_HISTORY);
				initSearchHistory();

			}
		});
		et_search = (EditText) findViewById(R.id.etSearch);
		btn_search = (TextView) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// // TODO Auto-generated method stub
				// if (TextUtils.isEmpty(et_search.getText())) {
				// Toast.makeText(getApplicationContext(), "«Î ‰»Îƒ⁄»›",
				// Toast.LENGTH_SHORT).show();
				// return;
				// }
				SharePreferenceHelp.getInstance(getApplicationContext())
						.saveSearchHistory(et_search.getText().toString(),
								SharePreferenceHelp.COMPANY_SEARCH_HISTORY);
				Intent intent = new Intent(CompanySearchActivity.this,
						CategoryDetailActivity.class);
				intent.putExtra("search", et_search.getText().toString());
				startActivity(intent);
				finish();

			}
		});
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		gv_hot = (GridView) findViewById(R.id.gv_hot);
		lv_history = (ListView) findViewById(R.id.lv_searchistory);
	}

	private void loadData() {
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				Common.COMPANY_HOT_SEARCH_URL, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get company info");
							mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
							return;
						}
						System.out.println("Result:" + response.toString());
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(response);

							Gson gson = new Gson();
							hotSearch = gson.fromJson(jsonObject.toString(),
									HotSearch.class);
							mHandler.sendEmptyMessage(MESSAGE_INIT_DATA_SUCCESS);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (isFinishing()) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
		}
	}

}
