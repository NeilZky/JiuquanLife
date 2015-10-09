package com.jiuquanlife.module.love;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.love.entity.SearchUserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SearchPeopleAdapter extends BaseAdapter {
	private List<SearchUserInfo> infos;
	private Activity context;
	private LayoutInflater mInflater;
	private String fromUid;

	public SearchPeopleAdapter(List<SearchUserInfo> publishInfos,
			Activity context) {
		super();
		this.infos = publishInfos;
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public SearchUserInfo getItem(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		// 优化listview
		if (convertView == null) {
			// 使用自定义的布局
			convertView = mInflater.inflate(R.layout.item_love_search, parent,
					false);
			holder = new ViewHolder();
			holder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
			holder.tv_hi = (ImageView) convertView.findViewById(R.id.tv_hi);
			holder.tv_shoucang = (ImageView) convertView
					.findViewById(R.id.tv_shoucang);
			holder.iv_love = (ImageView) convertView.findViewById(R.id.iv_love);

			holder.tv_distance = (TextView) convertView
					.findViewById(R.id.tv_distance);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_count);
			holder.tv_base2 = (TextView) convertView
					.findViewById(R.id.tv_base2);

			holder.tv_lastlogin = (TextView) convertView
					.findViewById(R.id.tv_lastlogin);
			holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
			holder.tv_baseinfo = (TextView) convertView
					.findViewById(R.id.tv_baseinfo);
			holder.tv_pic_count = (TextView) convertView
					.findViewById(R.id.tv_pic_count);
			holder.content_ll = (LinearLayout) convertView
					.findViewById(R.id.content_ll);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final SearchUserInfo searchUserInfo = infos.get(position);
		String imagePath = searchUserInfo.getHeadsavepath()
				+ searchUserInfo.getHeadsavename();

		ImageLoader.getInstance().displayImage(
				"http://www.5ijq.cn/public/uploads/" + imagePath,
				holder.iv_love, App.getOptions());
		String baseInfo = searchUserInfo.getLocation() + " "
				+ searchUserInfo.getBirthday() + "岁 "
				+ searchUserInfo.getStature() + "cm" + "\n"
				+ searchUserInfo.getEdu();
		holder.tv_baseinfo.setText(baseInfo);
		holder.tv_name.setText(searchUserInfo.getNickname());
		if (!TextUtils.isEmpty(searchUserInfo.getMateselection())) {
			holder.tv_desc.setText("正在寻找:" + searchUserInfo.getMateselection());
		}

		holder.tv_lastlogin.setText("上次登录时间 : "
				+ searchUserInfo.getLastLoginTime());
		holder.tv_count.setText(searchUserInfo.getViewnum());
		holder.tv_distance.setText(Utils.getDistance(searchUserInfo
				.getDistance()));
		final String zanNum = TextUtils.isEmpty(searchUserInfo.getZanNum()) ? "0"
				: searchUserInfo.getZanNum();
		holder.tv_zan.setText(zanNum);

		System.out.println("Can dian zan");
		holder.tv_zan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
					return;
				}

				if (TextUtils.isEmpty(searchUserInfo.getHasMyZan())
						|| "0".equals(searchUserInfo.getHasMyZan())) {
					v.setClickable(false);
					searchUserInfo.setHasMyZan("1");

					int zan = Integer.valueOf(zanNum) + 1;
					searchUserInfo.setZanNum(zan + "");
					holder.tv_zan.setText(zan + "");
					dianzan(searchUserInfo.getMaid());
				} else {
					Toast.makeText(context, "已经点过赞了", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		holder.tv_hi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
				}
				if (TextUtils.isEmpty(searchUserInfo.getHasHi())
						|| "0".equals(searchUserInfo.getHasHi())) {
					v.setClickable(false);
					searchUserInfo.setHasHi("1");
					sayHi(searchUserInfo.getUid());
				} else {
					Toast.makeText(context, "已经打过招呼了", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		holder.tv_shoucang.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(searchUserInfo.getHasFavorite())
						|| "0".equals(searchUserInfo.getHasFavorite())) {
					v.setClickable(false);
					searchUserInfo.setHasFavorite("1");
					shoucang(searchUserInfo.getMaid());
				} else {
					Toast.makeText(context, "已经收藏过了", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		holder.content_ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						PeopleDetailInfoActivity.class);
				intent.putExtra("mid", searchUserInfo.getMaid());
				intent.putExtra("uid", searchUserInfo.getUid());
				context.startActivity(intent);

			}
		});

		return convertView;
	}

	private void dianzan(String maid) {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_DIAN_ZAN);
		sb.append("/uid/");
		sb.append(fromUid);
		sb.append("/maid/");
		sb.append(maid);
		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(context, sb.toString(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");

							return;
						}
						System.out.println("Dian zan response:" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (jsonObject.has("info")) {
								Toast.makeText(context,
										jsonObject.getString("info"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private void sayHi(String uid) {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_SAY_HI);
		sb.append("/fromUid/");
		sb.append(fromUid);
		sb.append("/uid/");
		sb.append(uid);
		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(context, sb.toString(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");

							return;
						}
						System.out.println("Say hi response:" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (jsonObject.has("info")) {
								Toast.makeText(context,
										jsonObject.getString("info"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private void shoucang(String maid) {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_SHOUCHANGE);
		sb.append("/uid/");
		sb.append(fromUid);
		sb.append("/infoId/");
		sb.append(maid);
		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(context, sb.toString(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");

							return;
						}
						System.out.println("shoucang response:" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (jsonObject.has("info")) {
								Toast.makeText(context,
										jsonObject.getString("info"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	public void refreshData(List<SearchUserInfo> loveInfos) {
		infos = loveInfos;
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		TextView tv_zan, tv_distance, tv_name, tv_count, tv_base2,
				tv_lastlogin, tv_desc, tv_baseinfo, tv_pic_count;
		ImageView tv_hi, tv_shoucang, iv_love;
		LinearLayout content_ll;
	}
}
