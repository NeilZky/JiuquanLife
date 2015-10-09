package com.jiuquanlife.module.love;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.love.entity.BasicInfo;
import com.jiuquanlife.module.love.entity.CommonBaseInfo;
import com.jiuquanlife.module.love.entity.UpdateData;

public class PeopleDetailBaseInfoActivity extends BaseActivity {
	private Button tv_job, tv_shouru, tv_zuoxixiguan, tv_hunyin, tv_shengyu,
			tv_xingge, tv_jiating, tv_goufang, tv_gouche, tv_xiyan,
			tv_xingquaihao, tv_yinjiu;
	private EditText tv_tizhong, tv_searching, tv_aiqingxuanyan,
			tv_ziwojieshao, et_qq, et_tel, et_wechat;
	private CheckBox cb_open;
	private LinearLayout otherInfo;
	private RelativeLayout rl_submit;
	private ImageView back;
	private TextView btn_publish, btn_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love_baseinfo);
		parseIntent();
		initView();
		startProgressDialog("加载中...");
		loadData();

	}

	boolean isEdit = false;

	void parseIntent() {
		Intent intent = getIntent();
		if (null != intent) {
			mid = intent.getStringExtra("mid");
			uid = intent.getStringExtra("uid");
		}

		if (TextUtils.isEmpty(uid)) {
			finish();
		}

		isEdit = intent.getBooleanExtra("edit", false);

	}

	private UpdateData updateData;

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private static final int MSG_UPDATE_SUCCESS = 3;
	private static final int MSG_UPDATE_FAILED = 4;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:
				stopProgressDialog();
				Toast.makeText(PeopleDetailBaseInfoActivity.this, "未能找到数据",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			case MSG_INIT_DATA_SUCCESS:
				initViewData();
				stopProgressDialog();

				break;
			case MSG_UPDATE_SUCCESS:
				Intent intent = new Intent("com.jiuquan.updateBaseInfo");
				sendBroadcast(intent);
				stopProgressDialog();
				finish();
				break;
			case MSG_UPDATE_FAILED:
				stopProgressDialog();

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	void initViewData() {
		if (null == userInfo) {
			finish();
			return;
		}
		updateData = userInfo.getUpdateData();

		tv_job.setText(userInfo.getOccupation());
		tv_shouru.setText(userInfo.getIncome());
		tv_tizhong.setText(userInfo.getWeight());

		tv_zuoxixiguan.setText(userInfo.getHabits());
		tv_hunyin.setText(userInfo.getMarriagestatus());
		tv_shengyu.setText(userInfo.getBearstatus());
		tv_xingge.setText(userInfo.getCharacter());
		tv_jiating.setText(userInfo.getFamilystatus());
		tv_goufang.setText(userInfo.getHousestatus());
		tv_gouche.setText(userInfo.getCarstatus());
		tv_xiyan.setText(userInfo.getSmokestatus());

		tv_yinjiu.setText(userInfo.getDrinkstatus());

		tv_searching.setText(userInfo.getMateselection());
		tv_aiqingxuanyan.setText(userInfo.getLoveglow());
		tv_ziwojieshao.setText(userInfo.getResume());
		tv_xingquaihao.setText(convertHobby(userInfo.getHobby()));

		et_tel.setText(userInfo.getTelphone());
		et_qq.setText(userInfo.getQq());
		et_wechat.setText(userInfo.getWebchart());
		if ("0".equals(userInfo.getIspublic())) {
			cb_open.setChecked(false);
		} else {
			cb_open.setChecked(true);
		}
	}

	private String mid = Common.getMid();
	private String uid = Common.getUid();
	private String action = "update";

	private void loadData() {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_DETAIL_BASE_URL);
		sb.append("/mid/");
		sb.append(mid);
		sb.append("/uid/");
		sb.append(uid);
		sb.append("/action/");
		sb.append(action);
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

	private String convertHobby(String[] hobby) {
		StringBuilder sb = new StringBuilder("");
		if (null != hobby) {
			int size = hobby.length;
			for (int i = 0; i < size; i++) {
				sb.append(hobby[i] + " ");
			}
		}
		return sb.toString();
	}

	private BasicInfo userInfo;

	private void parse(String response) {
		System.out.println("result:" + response);
		try {

			Log.d(Common.TAG, "Detail data:" + response);
			JSONObject jsonObject = new JSONObject(response);

			if (jsonObject.has("data")) {
				jsonObject = jsonObject.getJSONObject("data");
				if (jsonObject.has("detialInfo")) {
					jsonObject = jsonObject.getJSONObject("detialInfo");
					System.out.println("detialInfo:::" + jsonObject);
					Gson gson = new Gson();
					userInfo = gson.fromJson(jsonObject.toString(),
							BasicInfo.class);
					if (null != userInfo) {
						System.out.println("userInfo:" + userInfo.toString());
						mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

					} else {
						mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

					}
				}
			}
		} catch (Exception e) {
			mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
			e.printStackTrace();
		}

	}

	private void initView() {

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		tv_job = (Button) findViewById(R.id.tv_job);

		tv_shouru = (Button) findViewById(R.id.tv_shouru);

		tv_tizhong = (EditText) findViewById(R.id.tv_tizhong);
		tv_zuoxixiguan = (Button) findViewById(R.id.tv_zuoxixiguan);
		tv_hunyin = (Button) findViewById(R.id.tv_hunyin);
		tv_shengyu = (Button) findViewById(R.id.tv_shengyu);
		tv_xingge = (Button) findViewById(R.id.tv_xingge);
		tv_jiating = (Button) findViewById(R.id.tv_jiating);
		tv_goufang = (Button) findViewById(R.id.tv_goufang);
		tv_gouche = (Button) findViewById(R.id.tv_gouche);
		tv_xiyan = (Button) findViewById(R.id.tv_xiyan);
		tv_yinjiu = (Button) findViewById(R.id.tv_yinjiu);
		tv_xingquaihao = (Button) findViewById(R.id.tv_xingquaihao);
		cb_open = (CheckBox) findViewById(R.id.cb_open);

		if (isEdit) {
			tv_tizhong.setEnabled(true);
			tv_tizhong.setFocusable(true);
			tv_hunyin.setOnClickListener(onClickListener);
			tv_zuoxixiguan.setOnClickListener(onClickListener);
			tv_shengyu.setOnClickListener(onClickListener);
			tv_xingge.setOnClickListener(onClickListener);
			tv_jiating.setOnClickListener(onClickListener);
			tv_goufang.setOnClickListener(onClickListener);
			tv_gouche.setOnClickListener(onClickListener);
			tv_xiyan.setOnClickListener(onClickListener);
			tv_job.setOnClickListener(onClickListener);
			tv_shouru.setOnClickListener(onClickListener);
			tv_yinjiu.setOnClickListener(onClickListener);
			tv_xingquaihao.setOnClickListener(onClickListener);
		} else {
			tv_tizhong.setEnabled(false);
			tv_tizhong.setFocusable(false);
		}
		btn_publish = (TextView) findViewById(R.id.btn_publish);
		btn_publish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postInfo();

			}
		});
		btn_cancel = (TextView) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		otherInfo = (LinearLayout) findViewById(R.id.otherInfo);
		rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);

		tv_searching = (EditText) findViewById(R.id.tv_searching);
		tv_ziwojieshao = (EditText) findViewById(R.id.tv_ziwojieshao);
		tv_aiqingxuanyan = (EditText) findViewById(R.id.tv_aiqingxuanyan);

		et_qq = (EditText) findViewById(R.id.et_qq);
		et_tel = (EditText) findViewById(R.id.et_tel);
		et_wechat = (EditText) findViewById(R.id.et_wechat);

		if (isEdit) {
			rl_submit.setVisibility(View.VISIBLE);
			otherInfo.setVisibility(View.VISIBLE);
		} else {
			tv_tizhong.setEnabled(false);
			tv_tizhong.setFocusable(false);
			rl_submit.setVisibility(View.GONE);
			otherInfo.setVisibility(View.GONE);
		}
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			showListDialog(v);

		}
	};
	private HashMap<String, String> map = null;
	private HashMap<String, String> selectMap = new HashMap<String, String>();

	private void showMulDialog(final View v, String title, String[] arrays,
			final List<CommonBaseInfo> resData) {
		String xingquaihao = tv_xingquaihao.getText().toString().trim();
		String[] hobby = xingquaihao.split(" ");
		boolean[] checked = null;
		map = new HashMap<String, String>();
		if (null == hobby) {

		} else {
			int size = hobby.length;
			checked = new boolean[resData.size()];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < resData.size(); j++) {
					CommonBaseInfo commonBaseInfo = resData.get(j);
					if (commonBaseInfo.getOpname().equals(hobby[i])) {
						checked[j] = true;
						map.put(commonBaseInfo.getOpvalue(),
								commonBaseInfo.getOpname());
					}
				}
			}

		}

		AlertDialog dialog = new AlertDialog.Builder(
				PeopleDetailBaseInfoActivity.this)
				.setTitle(title)
				.setMultiChoiceItems(arrays, checked,
						new OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								CommonBaseInfo content = resData.get(which);
								if (isChecked) {
									map.put(content.getOpvalue(),
											content.getOpname());
								} else {
									map.remove(content.getOpvalue());
								}
								// TextView tv = (TextView) v;
								// String text = tv.getText().toString();
								// String selectName = content.getOpname();
								// if (!isChecked) {
								// if (text.contains(selectName + " ")) {
								// text=text.replace(selectName + " ", "");
								// map.remove(content.getOpid());
								// }
								// } else {
								// if (!text.contains(selectName + " ")) {
								// text=text += selectName + " ";
								// map.put(content.getOpid(),
								// content.getOpname());
								// }
								//
								// }
								// tv.setText(text);

							}

						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectMap = map;
						Iterator<Entry<String, String>> iter = selectMap
								.entrySet().iterator();
						TextView tv = (TextView) v;
						StringBuilder sb = new StringBuilder();
						while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next();
							Object key = entry.getKey();
							String val = (String) entry.getValue();
							sb.append(val + " ");
						}
						tv.setText(sb.toString());
						dialog.dismiss();

					}
				}).create();

		dialog.show();
	}

	private void showListDialog(final View v) {
		String title = null;
		String[] arrays = null;
		List<CommonBaseInfo> commonBaseInfos = null;
		switch (v.getId()) {
		case R.id.tv_job:
			title = "选择职业";
			commonBaseInfos = updateData.getOccupation();
			arrays = covertListToArray(updateData.getOccupation());
			break;
		case R.id.tv_shouru:
			title = "选择收入";
			commonBaseInfos = updateData.getIncome();
			arrays = covertListToArray(updateData.getIncome());
			break;
		case R.id.tv_xingge:
			title = "性格特点";
			commonBaseInfos = updateData.getCharacter();
			System.out.println("Edu:" + updateData.getCharacter());
			arrays = covertListToArray(updateData.getCharacter());
			break;
		case R.id.tv_zuoxixiguan:
			title = "选择作息习惯";
			commonBaseInfos = updateData.getHabits();
			System.out.println("Habbits:" + updateData.getHabits());
			arrays = covertListToArray(updateData.getHabits());
			break;
		case R.id.tv_xiyan:
			title = "吸烟";
			commonBaseInfos = updateData.getSmoke();
			arrays = covertListToArray(updateData.getSmoke());
			// arrays = new String[] { "不吸，很反感吸烟", "社交时偶尔吸", "每周吸几次", "每天都吸",
			// "有烟瘾" };
			break;
		case R.id.tv_yinjiu:
			title = "饮酒";
			commonBaseInfos = updateData.getDrink();
			arrays = covertListToArray(updateData.getDrink());
			// arrays = new String[] { "不吸，很反感吸烟", "社交时偶尔吸", "每周吸几次", "每天都吸",
			// "有烟瘾" };
			break;
		case R.id.tv_gouche:
			title = "购车";
			commonBaseInfos = updateData.getCar();
			arrays = covertListToArray(updateData.getCar());
			break;
		case R.id.tv_hunyin:
			title = "婚姻";
			commonBaseInfos = updateData.getMarriage();
			arrays = covertListToArray(updateData.getMarriage());
			break;
		case R.id.tv_jiating:
			title = "家庭";
			commonBaseInfos = updateData.getFamily();
			arrays = covertListToArray(updateData.getFamily());
			// arrays = new String[] { "姐妹", "兄弟", "独生子女" };
			break;
		case R.id.tv_goufang:
			title = "购房";
			commonBaseInfos = updateData.getHouse();
			arrays = covertListToArray(updateData.getHouse());
			// arrays = new String[] { "已购", "无" };
			break;
		case R.id.tv_shengyu:
			title = "生育";
			commonBaseInfos = updateData.getBear();
			arrays = covertListToArray(updateData.getBear());
			break;
		case R.id.tv_xingquaihao:
			title = "兴趣爱好";
			commonBaseInfos = updateData.getHobby();
			arrays = covertListToArray(updateData.getHobby());
			showMulDialog(v, title, arrays, commonBaseInfos);

			return;

		default:
			break;
		}
		if (null == arrays) {
			return;
		}
		final List<CommonBaseInfo> res = commonBaseInfos;
		AlertDialog dialog = new AlertDialog.Builder(
				PeopleDetailBaseInfoActivity.this)
				.setTitle(title)
				.setSingleChoiceItems(arrays, 0,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								CommonBaseInfo content = res.get(which);
								((TextView) v).setText(content.getOpname());
								v.setTag(content.getOpvalue());

								dialog.dismiss();
							}

						}).create();
		dialog.show();

	}

	public String[] covertListToArray(List<CommonBaseInfo> baseInfoList) {
		int size = baseInfoList.size();
		String[] values = new String[size];
		for (int i = 0; i < baseInfoList.size(); i++) {
			values[i] = baseInfoList.get(i).toString();
		}
		return values;

	}

	private String convertMapToString() {
		Iterator<Entry<String, String>> iter = selectMap.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			sb.append(val + "=" + key);
			if (iter.hasNext()) {
				sb.append("-");
			}
		}
		System.out.println("hobby->" + sb.toString());
		return sb.toString();
	}

	private void postInfo() {

		String tizhong = tv_tizhong.getText().toString();
		if (TextUtils.isEmpty(tizhong)) {
			Toast.makeText(getApplicationContext(), "请输入体重", Toast.LENGTH_LONG)
					.show();
			return;
		}
		String searching = tv_searching.getText().toString();
		if (TextUtils.isEmpty(searching)) {
			Toast.makeText(getApplicationContext(), "请输入正在寻找",
					Toast.LENGTH_LONG).show();
			return;
		}

		String aiqingxuanyan = tv_aiqingxuanyan.getText().toString();
		if (TextUtils.isEmpty(aiqingxuanyan)) {
			Toast.makeText(getApplicationContext(), "请输入爱情宣言",
					Toast.LENGTH_LONG).show();
			return;
		}

		String ziwojieshao = tv_ziwojieshao.getText().toString();
		if (TextUtils.isEmpty(ziwojieshao)) {
			Toast.makeText(getApplicationContext(), "请输入自我介绍",
					Toast.LENGTH_LONG).show();
			return;
		}

		String qq = et_qq.getText().toString();

		String tel = et_tel.getText().toString();

		String wechat = et_wechat.getText().toString();

		boolean isOpen = cb_open.isChecked();

		if (isOpen) {
			if (TextUtils.isEmpty(wechat) && TextUtils.isEmpty(tel)
					&& TextUtils.isEmpty(qq)) {
				Toast.makeText(getApplicationContext(), "联系方式必须填一种",
						Toast.LENGTH_LONG).show();
				return;
			}
		}

		if (!TextUtils.isEmpty(tel)) {
			if (!isPhoneNumber(tel)) {
				Toast.makeText(getApplicationContext(), "电话号码格式不对",
						Toast.LENGTH_LONG).show();
				return;
			}
		}

		if (TextUtils.isEmpty(qq)) {
			if (!isQQ(qq)) {
				Toast.makeText(getApplicationContext(), "qq号码格式不对",
						Toast.LENGTH_LONG).show();
				return;
			}
		}

		String xiyan = (String) tv_xiyan.getTag();
		String hejiu = (String) tv_yinjiu.getTag();
		String gouche = (String) tv_gouche.getTag();
		String goufang = (String) tv_goufang.getTag();
		String hunyin = (String) tv_hunyin.getTag();
		String shengyu = (String) tv_shengyu.getTag();
		String jiating = (String) tv_jiating.getTag();
		String job = (String) tv_job.getTag();
		String shouru = (String) tv_shouru.getTag();
		String xingge = (String) tv_xingge.getTag();
		String xiguan = (String) tv_zuoxixiguan.getTag();
		String xingquaihao = convertMapToString();

		boolean isChecked = cb_open.isChecked();

		StringBuilder sb = new StringBuilder();

		sb.append(Common.LOVE_SAVE_USERINFO);
		sb.append("/maid/");
		sb.append(mid);
		sb.append("/uid/");
		sb.append(uid);

		if (!TextUtils.isEmpty(job)) {
			sb.append("/occupation/");

			sb.append(job.replace("/", "\\/"));
		}

		if (!TextUtils.isEmpty(shouru)) {
			sb.append("/income/");
			sb.append(shouru);
		}
		if (!TextUtils.isEmpty(tizhong)) {
			sb.append("/weight/");
			sb.append(tizhong);
		}
		if (!TextUtils.isEmpty(xiguan)) {
			sb.append("/habits/");
			sb.append(xiguan);
		}
		if (!TextUtils.isEmpty(hunyin)) {
			sb.append("/marriagestatus/");
			sb.append(hunyin);
		}
		if (!TextUtils.isEmpty(shengyu)) {
			sb.append("/bearstatus/");
			sb.append(shengyu);
		}
		if (!TextUtils.isEmpty(jiating)) {
			sb.append("/familystatus/");
			sb.append(jiating);
		}
		if (!TextUtils.isEmpty(goufang)) {
			sb.append("/housestatus/");
			sb.append(goufang);
		}
		if (!TextUtils.isEmpty(xingge)) {
			sb.append("/character/");
			sb.append(xingge);
		}
		if (!TextUtils.isEmpty(gouche)) {
			sb.append("/carstatus/");
			sb.append(gouche);
		}
		if (!TextUtils.isEmpty(xiyan)) {
			sb.append("/smokestatus/");
			sb.append(xiyan);
		}
		if (!TextUtils.isEmpty(hejiu)) {
			sb.append("/drinkstatus/");
			sb.append(hejiu);
		}

		sb.append("/ispublic/");
		sb.append(cb_open.isChecked() ? "1" : "0");

		if (!TextUtils.isEmpty(xingquaihao)) {
			sb.append("/hobby/");
			sb.append(xingquaihao);
		}

		if (!TextUtils.isEmpty(searching)) {
			sb.append("/mateselection/");
			sb.append(searching);
		}

		if (!TextUtils.isEmpty(aiqingxuanyan)) {
			sb.append("/loveglow/");
			sb.append(aiqingxuanyan);
		}
		if (!TextUtils.isEmpty(ziwojieshao)) {
			sb.append("/resume/");
			sb.append(ziwojieshao);
		}
		if (!TextUtils.isEmpty(qq)) {
			sb.append("/qq/");
			sb.append(qq);
		}
		if (!TextUtils.isEmpty(wechat)) {
			sb.append("/webchart/");
			sb.append(wechat);
		}
		if (!TextUtils.isEmpty(tel)) {
			sb.append("/telphone/");
			sb.append(tel);
		}

		System.out.println("Post Info:" + sb.toString());
		startProgressDialog("上传中...");
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				sb.toString(), new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");
							mHandler.sendEmptyMessage(MSG_UPDATE_FAILED);

							return;
						}
						System.out.println("Response:" + response);
						mHandler.sendEmptyMessage(MSG_UPDATE_SUCCESS);

					}

				});
	}

	private boolean isQQ(String input) {
		String regex = "^[1-9][0-9]{4,}$";
		Pattern p = Pattern.compile(regex);
		return Pattern.matches(regex, input);
	}

	private boolean isPhoneNumber(String input) {
		String regex = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
		Pattern p = Pattern.compile(regex);
		return Pattern.matches(regex, input);
	}
}
