package com.jiuquanlife.module.love;

import io.rong.imlib.model.UserInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.love.entity.FriendInfo;
import com.jiuquanlife.module.love.entity.FromInfo;
import com.jiuquanlife.module.love.wode.CharacterParser;
import com.jiuquanlife.module.love.wode.ClearEditText;
import com.jiuquanlife.module.love.wode.PinyinComparator;
import com.jiuquanlife.module.love.wode.SideBar;
import com.jiuquanlife.module.love.wode.SideBar.OnTouchingLetterChangedListener;
import com.jiuquanlife.module.love.wode.SortAdapter;
import com.jiuquanlife.module.love.wode.SortModel;

public class LoveFriendListActivity extends BaseActivity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * ����ƴ��������ListView�����������
	 */
	private PinyinComparator pinyinComparator;
	private ImageView back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_love_wode);
		initViews();
		startProgressDialog("������...");
		loadData();
	}

	private void initViews() {

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		// ʵ��������תƴ����
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
				Toast.makeText(
						getApplicationContext(),
						((FriendInfo) adapter.getItem(position)).getFusername(),
						Toast.LENGTH_SHORT).show();
			}
		});

		adapter = new SortAdapter(getApplicationContext(),
				new ArrayList<FriendInfo>());
		sortListView.setAdapter(adapter);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				FriendInfo friendInfo = adapter.getItem(position);
				// FromInfo fromInfo = friendInfo.getFromInfo();
				// RongIM.getInstance().startConversation(
				// LoveFriendListActivity.this,
				// Conversation.ConversationType.PRIVATE,
				// fromInfo.getUid(), "Demo for test");

				Intent intent = new Intent(LoveFriendListActivity.this,
						PeopleDetailInfoActivity.class);
				System.out.println("mid:" + friendInfo.getMaid() + "  uid:"
						+ friendInfo.getUid());
				intent.putExtra("mid", friendInfo.getFromInfo().getMaid());
				intent.putExtra("uid", friendInfo.getFromInfo().getUid());
				startActivity(intent);
			}
		});

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// �������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	/**
	 * ΪListView�������
	 * 
	 * @param date
	 * @return
	 */
	private List<FriendInfo> filledData(List<FriendInfo> friendInfos) {

		for (int i = 0; i < friendInfos.size(); i++) {
			FriendInfo friendInfo = friendInfos.get(i);
			// ����ת����ƴ��
			String pinyin = characterParser.getSelling(friendInfo
					.getFusername());
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if (sortString.matches("[A-Z]")) {
				friendInfo.setSortLetters(sortString.toUpperCase());
			} else {
				friendInfo.setSortLetters("#");
			}

		}
		return friendInfos;
	}

	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<FriendInfo> filterDateList = new ArrayList<FriendInfo>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = friendInfos;
		} else {
			filterDateList.clear();
			for (FriendInfo sortModel : friendInfos) {
				String name = sortModel.getFusername();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:
				stopProgressDialog();
				Toast.makeText(LoveFriendListActivity.this, "δ���ҵ�����",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			case MSG_INIT_DATA_SUCCESS:
				initViewData();
				stopProgressDialog();

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void initViewData() {
		if (null == friendInfos)
			return;
		friendInfos = filledData(friendInfos);
		// ����a-z��������Դ����
		Collections.sort(friendInfos, pinyinComparator);
		adapter.updateListView(friendInfos);
	}

	private String uid = Common.getUid();
	private List<FriendInfo> friendInfos;

	private void loadData() {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_FRIEND_LIST);
		sb.append("/uid/");
		sb.append(uid);
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

	public static Map<String, UserInfo> UserInfos = new HashMap<String, UserInfo>();

	private void transferFriendInfoToUserInfo() {
		Iterator<FriendInfo> ite = friendInfos.iterator();
		int i = 1;
		UserInfos = new HashMap<String, UserInfo>();
		while (ite.hasNext()) {

			FriendInfo friendInfo = ite.next();
			FromInfo fromInfo = friendInfo.getFromInfo();
			String imagePath = fromInfo.getHeadsavepath()
					+ fromInfo.getHeadsavename();
			System.out.println("imagePath:"
					+ "http://www.5ijq.cn/public/uploads/" + imagePath);

			imagePath = "http://www.5ijq.cn/public/uploads/" + imagePath;
			UserInfo userInfo = new UserInfo(fromInfo.getUid(),
					fromInfo.getNickname(), Uri.parse(imagePath));
			if (i == 1) {
				UserInfo userInfo2 = new UserInfo("126", "demo",
						Uri.parse(imagePath));
				UserInfos.put("126", userInfo2);
			}
			UserInfos.put(fromInfo.getUid(), userInfo);
			i++;
		}
	}

	private void parse(String response) {
		System.out.println("result:" + response);
		try {

			Log.d(Common.TAG, "Detail data:" + response);
			JSONObject jsonObject = new JSONObject(response);

			if (jsonObject.has("data")) {
				Type listType = new TypeToken<ArrayList<FriendInfo>>() {
				}.getType();
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				Gson gson = new Gson();
				friendInfos = gson.fromJson(jsonArray.toString(), listType);
				if (null != friendInfos) {
					System.out.println("userInfo:" + friendInfos.toString());
					transferFriendInfoToUserInfo();
					mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

				} else {
					mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
