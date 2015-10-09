package com.jiuquanlife.module.company.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.activity.RefreshListView.IOnLoadMoreListener;
import com.jiuquanlife.module.company.adapter.CommentAdapter;
import com.jiuquanlife.module.company.entity.CommentInfo;

public class CommentListActivity extends BaseActivity {
	private RefreshListView mListView;
	private CommentAdapter adapter;
	private Button btn;
	private List<CommentInfo> commentInfos;
	private String companyId, title;
	private int page = 0, rate = 0;
	private TextView tv_title;
	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private static final int MSG_LOAD_DATA = 3;
	ImageView iv_rate1, iv_rate2, iv_rate3, iv_rate4, iv_rate5;
	private LinearLayout ll_back;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:

				break;
			case MSG_INIT_DATA_SUCCESS:
				stopProgressDialog();
				adapter.refreshData(commentInfos);
				mListView.onRefreshComplete();
				break;
			case MSG_LOAD_DATA:

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_list);
		mListView = (RefreshListView) findViewById(R.id.listView);
		mListView.setOnLoadMoreListener(iLoadMoreListener);
		commentInfos = new ArrayList<CommentInfo>();
		adapter = new CommentAdapter(commentInfos, this, mListView);
		mListView.setAdapter(adapter);
		initView();
		parseIntent();
		startProgressDialog("º”‘ÿ÷–...");
		loadData();

	}

	private void initView() {
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		iv_rate1 = (ImageView) findViewById(R.id.iv_rate1);
		iv_rate2 = (ImageView) findViewById(R.id.iv_rate2);
		iv_rate3 = (ImageView) findViewById(R.id.iv_rate3);
		iv_rate4 = (ImageView) findViewById(R.id.iv_rate4);
		iv_rate5 = (ImageView) findViewById(R.id.iv_rate5);
		tv_title = (TextView) findViewById(R.id.tv_title);

		btn = (Button) findViewById(R.id.tv_comment);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(getApplicationContext(), "«Îœ»µ«¬º",
							Toast.LENGTH_SHORT).show();
					return;
				}

				Intent intent = new Intent(CommentListActivity.this,
						CompanyCommentActivity.class);
				intent.putExtra("id", companyId);
				startActivity(intent);
				finish();

			}
		});
	}

	private RefreshListView.IOnLoadMoreListener iLoadMoreListener = new IOnLoadMoreListener() {

		@Override
		public void OnLoadMore() {
			page++;

			loadData();
		}
	};

	private void parseIntent() {
		Intent intent = getIntent();
		companyId = intent.getStringExtra("companyId");
		title = intent.getStringExtra("title");
		rate = intent.getIntExtra("rate", 0);

		tv_title.setText(title);
	}

	private void loadData() {
		StringBuilder sb = new StringBuilder(Common.COMMENT_LIST_URL);
		sb.append("/companyId/");
		sb.append(companyId);
		sb.append("/page/");
		sb.append(page);
		System.out.println("Request url:" + sb.toString());
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				sb.toString(), responseListener);
	}

	private Listener<String> responseListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {

			if (TextUtils.isEmpty(response)) {
				mListView.onLoadMoreComplete(true);
				Log.d(Common.TAG, "Failed to get company info");
				mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
				return;
			}

			parse(response);
			mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

		}
	};

	private void parse(String result) {
		Log.d(Common.TAG, "Detail data:" + result);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			if (jsonObject.has("data")) {

				JSONArray dataJsonObject = (JSONArray) jsonObject.get("data");
				if (null != dataJsonObject && dataJsonObject.length() > 0) {
					mListView.onLoadMoreComplete(false);
				} else {
					mListView.onLoadMoreComplete(true);
				}
				Gson gson = new Gson();
				for (int i = 0; i < dataJsonObject.length(); i++) {
					JSONObject jsonObject2 = dataJsonObject.getJSONObject(i);
					CommentInfo commentInfo = gson.fromJson(
							jsonObject2.toString(), CommentInfo.class);
					commentInfos.add(commentInfo);
					System.out.println("CommentList:" + commentInfo.toString());
				}
			}

		} catch (Exception e) {
			mListView.onLoadMoreComplete(true);
			e.printStackTrace();

		}
	}
}
