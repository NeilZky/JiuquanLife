package com.jiuquanlife.module.publish;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.publish.adapter.PublishTypeAdapter;
import com.jiuquanlife.module.publish.adapter.SubTypeAdapter;
import com.jiuquanlife.module.publish.entity.TypeGroupInfo;
import com.jiuquanlife.module.publish.entity.TypeInfo;
import com.jiuquanlife.module.publish.entity.TypesInfo;

public class PublishTypeActivity extends BaseActivity {
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
				initViewData();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
	private PublishTypeAdapter typeAdapter, typeAdapter2;
	private SubTypeAdapter typeInfoAdapter, typeInfoAdapter2;
	List<TypeGroupInfo> infos, firstInfos, secondInfos;

	private void initViewData() {
		infos = typesInfo.getTypeInfos();
		firstInfos = infos.size() >= 4 ? infos.subList(0, 4) : infos;
		secondInfos = infos.size() >= 4 ? infos.subList(4, infos.size())
				: new ArrayList<TypeGroupInfo>();
		typeAdapter = new PublishTypeAdapter(firstInfos, this);
		typeAdapter2 = new PublishTypeAdapter(secondInfos, this);
		gvType.setAdapter(typeAdapter);
		gvType2.setAdapter(typeAdapter2);
		gvType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				gvSubType2.setVisibility(View.GONE);
				v_line1.setVisibility(View.VISIBLE);
				v_line2.setVisibility(View.GONE);
				gvSubType.setVisibility(View.VISIBLE);
				typeAdapter.setSelectedItem(position);
				typeAdapter2.setSelectedItem(-1);
				
				if (position == 0) {
					gvSubType.setVisibility(View.GONE);
					Intent intent = new Intent(PublishTypeActivity.this,
							PublishActivity.class);
					intent.putExtra("type", infos.get(position).getTypeId());
					startActivity(intent);
					return;
				}
				
				final TypeGroupInfo typeGroupInfo = infos.get(position);
				final List<TypeInfo> tyInfos = infos.get(position)
						.getSubInfoTypeList();
				System.out.println("tyInfos:" + tyInfos);
				typeInfoAdapter = new SubTypeAdapter(tyInfos,
						PublishTypeActivity.this);
				gvSubType.setAdapter(typeInfoAdapter);
				typeInfoAdapter.notifyDataSetChanged();
				gvSubType.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						typeInfoAdapter.setMark(position);
						TypeInfo subTypeInfo = tyInfos.get(position);
						Intent intent = new Intent(PublishTypeActivity.this,
								PublishActivity.class);
						intent.putExtra("type", typeGroupInfo.getTypeId());
						intent.putExtra("subTypeId", subTypeInfo.getTypeId());
						startActivity(intent);

					}
				});
				typeInfoAdapter.setMark(-1);
			}
		});
		typeAdapter.setSelectedItem(-1);
		gvType2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				TypeGroupInfo groupInfo = secondInfos.get(position);
				v_line1.setVisibility(View.GONE);
				v_line2.setVisibility(View.VISIBLE);
				gvSubType.setVisibility(View.GONE);
				gvSubType2.setVisibility(View.VISIBLE);
				typeAdapter.setSelectedItem(-1);
				typeAdapter2.setSelectedItem(position);
				if (null == groupInfo.getSubInfoTypeList()
						|| groupInfo.getSubInfoTypeList().isEmpty()) {
					gvSubType2.setVisibility(View.GONE);
					Intent intent = new Intent(PublishTypeActivity.this,
							PublishActivity.class);
					intent.putExtra("type", groupInfo.getTypeId());
					startActivity(intent);
					return;
				}
				
				final TypeGroupInfo typeGroupInfo = secondInfos.get(position);
				final List<TypeInfo> tyInfos = secondInfos.get(position)
						.getSubInfoTypeList();
				System.out.println("tyInfos:" + tyInfos);
				if (null == tyInfos || tyInfos.size() == 0) {
					gvSubType2.setVisibility(View.GONE);
					return;
				}
				typeAdapter.setSelectedItem(-1);
				typeAdapter2.setSelectedItem(position);
				typeInfoAdapter2 = new SubTypeAdapter(tyInfos,
						PublishTypeActivity.this);
				gvSubType2.setAdapter(typeInfoAdapter2);
				typeInfoAdapter2.notifyDataSetChanged();
				gvSubType2.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						typeInfoAdapter2.setMark(position);
						TypeInfo subTypeInfo = tyInfos.get(position);
						Intent intent = new Intent(PublishTypeActivity.this,
								PublishActivity.class);
						intent.putExtra("type", typeGroupInfo.getTypeId());
						intent.putExtra("subTypeId", subTypeInfo.getTypeId());
						startActivity(intent);

					}
				});
				typeInfoAdapter2.setMark(-1);
			}
		});
		typeAdapter2.setSelectedItem(-1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_type);
		System.out.println("Recall oncreate");
		initView();

		loadData();
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		System.out.println("onResume");
		super.onResume();
	}


	private GridView gvType, gvSubType, gvType2, gvSubType2;
	private View v_line1, v_line2;

	private void initView() {
		v_line1 = findViewById(R.id.v_line1);
		v_line2 = findViewById(R.id.v_line2);
		gvType = (GridView) findViewById(R.id.gv_type);

		gvSubType = (GridView) findViewById(R.id.gv_subtype);
		gvType2 = (GridView) findViewById(R.id.gv_type2);

		gvSubType2 = (GridView) findViewById(R.id.gv_subtype2);
	}

	private void loadData() {
		startProgressDialog("Мгдижа...");
		StringBuilder sb = new StringBuilder();
		sb.append(Common.PUBLISH_TYPES);
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

	private TypesInfo typesInfo;

	private void parse(String result) {
		try {
			Log.d(Common.TAG, "Detail data:" + result);
			JSONObject jsonObject = new JSONObject(result);

			Gson gson = new Gson();
			typesInfo = gson.fromJson(jsonObject.toString(), TypesInfo.class);
			if (null != typesInfo) {
				mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

			} else {
				mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
			}

		} catch (Exception e) {
			mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PublishTypeActivity.this,
				PublishActivity.class);
		startActivity(intent);
		super.onBackPressed();
	}

	
}
