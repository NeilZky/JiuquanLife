package com.jiuquanlife.module.company.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.BuildConfig;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.Util;
import com.jiuquanlife.module.company.adapter.CommentAdapter;
import com.jiuquanlife.module.company.adapter.ProductAdapter;
import com.jiuquanlife.module.company.entity.CompanyDetailInfo;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class CompanyDetailActivity extends Activity {
	private static final int MESSAGE_INIT_DATA_SUCCESS = 1;
	private static final int MSG_INIT_DATA_FAILED = 2;
	private LinearLayout ll_back, ll_rate, ll_company_env;
	private TextView tv_desc, tv_visitnum, tv_addresss, tv_title, tv_product;
	private ImageView iv_company_pic;
	private RelativeLayout ll_company_brief;
	private ImageView iv_rate1, iv_rate2, iv_rate3, iv_rate4, iv_rate5,
			iv_phone_icon, iv_company_img;
	private ListView lv_comments, lv_products;
	private View v_line;
	CompanyDetailInfo companyDetailInfo;
	String companyId;
	private Button btn_comment;
	private ScrollView sv_first_sc;
	private ArrayList<ImgInfo> environmentImgInfos;
	private BroadcastReceiver refreshBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			System.out.println("receive refresh");
			loadData(companyId);

		}
	};
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_INIT_DATA_SUCCESS:

				setViewValue();
				break;
			case MSG_INIT_DATA_FAILED:
				Toast.makeText(getApplicationContext(), "加载数据失败",
						Toast.LENGTH_LONG).show();
				finish();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void setViewValue() {

		ll_rate.setVisibility(View.VISIBLE);
		tv_addresss.setText(companyDetailInfo.getAddress());
		tv_title.setText(companyDetailInfo.getCompany());

		String descInfo = Html.fromHtml(companyDetailInfo.getIntro())
				.toString();
		;
		tv_desc.setText(descInfo);

		int length = String.valueOf(companyDetailInfo.getVisitNum()).length();
		tv_visitnum.setText("已有 " + companyDetailInfo.getVisitNum() + " 人浏览过");
		SpannableStringBuilder builder = new SpannableStringBuilder(tv_visitnum
				.getText().toString());

		// ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
		builder.setSpan(redSpan, 3, 3 + length,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_visitnum.setText(builder);
		ImageLoader.getInstance().loadImage(
				Common.PIC_PREFX + companyDetailInfo.getPic(),
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap bitMap) {
						System.out.println("finish loading");
						Drawable drawable = new BitmapDrawable(bitMap);
						ll_company_brief.setBackground(drawable);

					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						// TODO Auto-generated method stub

					}
				});
		
		if (null != companyDetailInfo.getEnvirenmentImg()
				&& !companyDetailInfo.getEnvirenmentImg().isEmpty()) {
			environmentImgInfos = (ArrayList<ImgInfo>) companyDetailInfo
					.getEnvirenmentImg();
			ImgInfo firstImg = environmentImgInfos.get(0);

			ImageLoader.getInstance().loadImage(
					Common.PIC_PREFX + firstImg.getPic(),
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap bitMap) {
							System.out.println("finish loading");
							Drawable drawable = new BitmapDrawable(bitMap);
							ll_company_env.setBackground(drawable);

						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
							// TODO Auto-generated method stub

						}
					});
			ll_company_env.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(CompanyDetailActivity.this,
							CompanyGalleryActivity.class);
					System.out.println("Env img:" + environmentImgInfos);
					intent.putParcelableArrayListExtra("imgInfos",
							environmentImgInfos);
					startActivity(intent);
				}
			});
		}

		int size = companyDetailInfo.getCommentList() == null ? 0
				: companyDetailInfo.getCommentList().size();
		System.out.println("Comment list size:" + size);

		if (null == companyDetailInfo.getProductList()
				|| companyDetailInfo.getProductList().isEmpty()) {
			//
		} else {
			v_line.setVisibility(View.VISIBLE);
			tv_product.setVisibility(View.VISIBLE);
			lv_products.setVisibility(View.VISIBLE);
			ProductAdapter productAdapter = new ProductAdapter(
					companyDetailInfo.getProductList(), this);
			lv_products.setAdapter(productAdapter);
			Util.setListViewHeightBasedOnChildren(lv_products);
		}
		if (null != companyDetailInfo.getCommentList()
				&& companyDetailInfo.getCommentList().size() == 3) {
			System.out.println("Add view");
			initLoadMoreView(this);
		}
		CommentAdapter commentAdapter = new CommentAdapter(
				companyDetailInfo.getCommentList(), this, lv_comments);
		lv_comments.setAdapter(commentAdapter);

		if (null != companyDetailInfo.getCommentList()
				&& companyDetailInfo.getCommentList().size() == 3) {
			Util.setListViewHeightBasedOnChildren2(lv_comments);
		} else {
			Util.setListViewHeightBasedOnChildren2(lv_comments);
		}

		sv_first_sc = (ScrollView) findViewById(R.id.sv_first_sc);
		sv_first_sc.scrollTo(0, -100);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_detail);
		initView();
		// initLoadMoreView(this);
		parseIntent();
		IntentFilter intentFilter = new IntentFilter(
				"com.jiuquan.updateCompanyInfo");
		registerReceiver(refreshBroadcastReceiver, intentFilter);
		loadData(companyId);

	}

	private void parseIntent() {
		Intent intent = getIntent();

		companyId = intent.getStringExtra("companyId");
		System.out.println("intentId:" + companyId);

	}

	private void initView() {
		ll_company_env = (LinearLayout) findViewById(R.id.ll_company_environment);
		v_line = findViewById(R.id.v_line);
		tv_product = (TextView) findViewById(R.id.tv_product);
		btn_comment = (Button) findViewById(R.id.btn_comment);
		btn_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(companyId)) {
					return;
				}
				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
					return;
				}

				Intent intent = new Intent(CompanyDetailActivity.this,
						CompanyCommentActivity.class);
				intent.putExtra("id", companyId);
				startActivity(intent);

			}
		});
		iv_phone_icon = (ImageView) findViewById(R.id.iv_phone_icon);
		iv_phone_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (null == companyDetailInfo) {
					return;
				}
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ companyDetailInfo.getTel()));
				CompanyDetailActivity.this.startActivity(intent);

			}
		});
		ll_rate = (LinearLayout) findViewById(R.id.ll_rate);
		ll_company_brief = (RelativeLayout) findViewById(R.id.ll_company_brief);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_visitnum = (TextView) findViewById(R.id.tv_visitnum);
		tv_addresss = (TextView) findViewById(R.id.tv_address);
		tv_title = (TextView) findViewById(R.id.tv_title);

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
		lv_comments = (ListView) findViewById(R.id.lv_comments);
		lv_products = (ListView) findViewById(R.id.lv_productlist);
	}

	private void loadData(String id) {
		System.out.println("url;" + Common.COMPANY_DETAIL_URL + id);
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				Common.COMPANY_DETAIL_URL + String.valueOf(id),
				responseListener);
	}

	private Listener<String> responseListener = new Listener<String>() {

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

				if (jsonObject.has("data")) {
					jsonObject = jsonObject.getJSONObject("data");

					Gson gson = new Gson();
					companyDetailInfo = gson.fromJson(jsonObject.toString(),
							CompanyDetailInfo.class);
					mHandler.sendEmptyMessage(MESSAGE_INIT_DATA_SUCCESS);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isFinishing()) {
			unregisterReceiver(refreshBroadcastReceiver);

		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (isFinishing()) {
			unregisterReceiver(refreshBroadcastReceiver);
		}
	}

	View mFootView;
	View mLoadMoreView;
	TextView mLoadMoreTextView;
	View mLoadingView;

	// 初始化footview试图
	private void initLoadMoreView(Context context) {
		mFootView = LayoutInflater.from(context).inflate(R.layout.loadmore,
				null);

		mLoadMoreView = mFootView.findViewById(R.id.load_more_view);

		mLoadMoreTextView = (TextView) mFootView
				.findViewById(R.id.load_more_tv);
		mLoadMoreTextView.setTextColor(getResources().getColor(
				R.color.black_black));
		mLoadingView = mFootView.findViewById(R.id.loading_layout);

		mLoadMoreView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CompanyDetailActivity.this,
						CommentListActivity.class);
				intent.putExtra("companyId", companyId);
				if (null != companyDetailInfo) {
					intent.putExtra("rate", 1);
					intent.putExtra("title", companyDetailInfo.getCompany());
				}
				startActivity(intent);

			}
		});

		lv_comments.addFooterView(mFootView);

	}
}
