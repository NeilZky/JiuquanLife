package com.jiuquanlife.module.publish;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.entity.AddressInfo;
import com.jiuquanlife.module.publish.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.module.publish.PullToRefreshView.OnHeaderRefreshListener;
import com.jiuquanlife.module.publish.adapter.AddressAdapter;
import com.jiuquanlife.module.publish.adapter.PublishAdapter;
import com.jiuquanlife.module.publish.entity.PublishContent;
import com.jiuquanlife.module.publish.entity.PublishInfo;

public class PublishActivity extends BaseActivity {
	private TextView tvTitle;
	private LinearLayout ll_dropdown;
	private TextView tv_publish;
	private RelativeLayout rl_publish;

	private PullToRefreshView pullToRefreshView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish);
		initView();
		startProgressDialog("努力加载中...");
		parseIntent();
		loadData();
	}

	private String typeId = "0", subTypeId = "0";

	private void parseIntent() {
		Intent intent = getIntent();
		page = 0;
		key = intent.getStringExtra("search");
		etSearch.setText(key);
		typeId = intent.getStringExtra("type");
		subTypeId = intent.getStringExtra("subTypeId");

	}

	

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:

				break;
			case MSG_INIT_DATA_SUCCESS:
				stopProgressDialog();
				publishAdapter.refreshData(publishInfos);
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
	private int addressId = 0;
	private int page = 12;
	private String key;

	private void loadData() {

		StringBuilder sb = new StringBuilder();
		sb.append(Common.PUBLISH_INDEX);
		sb.append("/page/");
		sb.append(page);
		sb.append("/address/");
		sb.append(addressId);
		if (!TextUtils.isEmpty(typeId)) {
			sb.append("/type/");
			sb.append(typeId);
			if (!typeId.equals("0")) {
				sb.append("/subType/");
				sb.append(subTypeId);
			}
		}

		if (!TextUtils.isEmpty(key)) {
			sb.append("/key/");
			sb.append(Uri.encode(key));
		}
		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				sb.toString(), new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");
							mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

							return;
						}
						parse(response);

					}
				});

	}

	private List<PublishInfo> publishInfos = new ArrayList<PublishInfo>();
	private List<AddressInfo> addressInfos;

	private void parse(String result) {
		try {

			Log.d(Common.TAG, "Detail data:" + result);
			JSONObject jsonObject = new JSONObject(result);

			if (jsonObject.has("data")) {
				jsonObject = jsonObject.getJSONObject("data");

				Gson gson = new Gson();
				PublishContent publishContent = gson.fromJson(
						jsonObject.toString(), PublishContent.class);
				if (null != publishContent) {
					mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);
					if (null == addressInfos) {
						addressInfos = publishContent.getAddressList();
					}
					if (null != publishContent.getInfoList()
							&& !publishContent.getInfoList().isEmpty()) {
						publishInfos.addAll(publishContent.getInfoList());

					} else {

						Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
					}
				} else {

					mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

				}
				System.out.println("publishContent:" + publishContent);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private ImageView ivType;
	private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			System.out.println("onheader refresh");
			page = 0;
			publishInfos = new ArrayList<PublishInfo>();

			publishAdapter.refreshData(publishInfos);
			loadData();

		}
	};

	private OnFooterRefreshListener onFooterRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			System.out.println("onFootRefresh");
			page++;
			loadData();

		}
	};

	private void initView() {
		pullToRefreshView = (PullToRefreshView) findViewById(R.id.pl_pub);
		pullToRefreshView.setPullDownEnable(true);
		pullToRefreshView.setPullUpEnable(true);
		pullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
		pullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);
		rl_publish =(RelativeLayout) findViewById(R.id.rl_publish);	
	
		rl_publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(PublishActivity.this,
						PublishInfoActivity.class);
				startActivity(intent);
			}
		});
		ivType = (ImageView) findViewById(R.id.ivSelect);
		ivType.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PublishActivity.this,
						PublishTypeActivity.class);
				startActivity(intent);
				finish();
			}
		});

		tvTitle = (TextView) findViewById(R.id.tv_title);
		ll_dropdown = (LinearLayout) findViewById(R.id.ll_back);
		ll_dropdown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (null == addressInfos || addressInfos.isEmpty()) {
					return;
				}
				showAddressPopupWindow(ll_dropdown.getWidth(),
						ll_dropdown.getHeight(), 1, 1);

			}
		});
		mListView = (ListView) findViewById(R.id.lv_pl);
		mListView.setOnTouchListener(onTouchListener);
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int arg1) {
				switch (arg1) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					if(view.getLastVisiblePosition()==(view.getCount()-1))
					{
						isShowBtn = false;
						rl_publish.setVisibility(View.GONE);
						
						System.out.println("is last");
					}
					break;

				default:
					break;
				}
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		publishAdapter = new PublishAdapter(publishInfos, PublishActivity.this);
		mListView.setAdapter(publishAdapter);

		etSearch = (EditText) findViewById(R.id.etSearch);
		etSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(PublishActivity.this,
						PublishSearchActivity.class);
				startActivity(intent);

			}
		});
	}

	float downY = 0;
	boolean isActionDown = false, isShowBtn=true;
	private OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isActionDown = true;
				downY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				if (!isActionDown) {
					isActionDown = true;
					downY = event.getY();
				} else {
					float currentY = event.getY();
					if (currentY - downY < -20 && isShowBtn) {
						isShowBtn = false;
						rl_publish.setVisibility(View.GONE);
					} else if (currentY - downY > 20 && !isShowBtn) {
						isShowBtn = true;
						// up 
						rl_publish.setVisibility(View.VISIBLE);
					}
				}
			default:
				break;
			}
			return false;
		}
	};

	private EditText etSearch;
	private PublishAdapter publishAdapter;
	private ListView mListView;
	AddressAdapter addressAdapter;
	private LinearLayout ll_popup, ll_address;
	private ListView popRootList, lv_pl;
	private FrameLayout flChild;
	private PopupWindow mPopWin;

	private void showAddressPopupWindow(int width, int height, int px, int py) {
		ll_popup = (LinearLayout) LayoutInflater.from(PublishActivity.this)
				.inflate(R.layout.popup_one_category, null);
		popRootList = (ListView) ll_popup.findViewById(R.id.rootcategory);
		addressAdapter = new AddressAdapter(addressInfos, this);
		addressAdapter.setOrderId(addressId);
		popRootList.setAdapter(addressAdapter);
		popRootList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				addressId = addressAdapter.getItem(position).getAid();
				addressAdapter.setOrderId(addressId);
				tvTitle.setText(addressAdapter.getItem(position)
						.getAddressName());
				addressAdapter.notifyDataSetChanged();
				mPopWin.dismiss();
				key = "";
				etSearch.setText("");
				typeId = "0";
				subTypeId = "0";
				page = 0;
				publishInfos = new ArrayList<PublishInfo>();
				publishAdapter.notifyDataSetChanged();
				loadData();
			}
		});
		addressAdapter.notifyDataSetChanged();

		mPopWin = new PopupWindow(ll_popup, 300, 750, true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
		mPopWin.showAsDropDown(ll_dropdown, -40, 1);
		mPopWin.update();
	}

}
