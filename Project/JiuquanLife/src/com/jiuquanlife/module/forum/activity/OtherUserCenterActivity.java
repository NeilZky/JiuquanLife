package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.view.CircleImageView;
import com.jiuquanlife.vo.forum.usercenter.UserCenterJson;
import com.jiuquanlife.vo.forum.usercenter.UserData;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OtherUserCenterActivity extends BaseActivity{
	
	private CircleImageView civ_photo_user_center;
	private UserCenterJson userCenterJson;
	private int uid;
	
	public static final String EXTRA_UID = "EXTRA_UID";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		
		initViews();
		initData();
		getData();
	}

	private void initData() {
		
		uid = getIntent().getIntExtra(EXTRA_UID, 0);
	}

	private void initViews() {
	
		setContentView(R.layout.activity_other_user_center);
		civ_photo_user_center = (CircleImageView) findViewById(R.id.civ_photo_user_center);
	}
	
	
	public void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userinfo");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		if(user!=null) {
			map.put("accessToken", user.token);
			map.put("accessSecret", user.secret);
			map.put("userId", uid + "");
		}
		map.put("appHash", mAppHash);
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						userCenterJson = GsonUtils.toObj(response, UserCenterJson.class);
						fillView(userCenterJson);
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
					}
				});
	}

	protected void fillView(UserCenterJson json) {
		
		if(json==null) {
			return;
		}
		setText(R.id.tv_user_title_auc, json.userTitle);
		ImageLoader.getInstance().displayImage( json.icon, civ_photo_user_center);
		if(json.body!=null && json.body.creditShowList!=null) {
			for(UserData ud : json.body.creditShowList) {
				if("credits".equals(ud.type)) {
					setText(R.id.tv_score_user_center, ud.data);
				}
				if("extcredits2".equals(ud.type)) {
					setText(R.id.tv_money_user_center, ud.data);
				}
			}
		}
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_profile_mine:
			onClickProfile();
			break;
		case R.id.ll_album_mine:
			onClickAlbum();
			break;
		case R.id.ll_follow_mine:
			startUserListActivity( UserListActivity.TYPE_FOLLOW, "ta的关注");
			break;
		case R.id.ll_followed_mine:
			startUserListActivity( UserListActivity.TYPE_FOLLOWED , "ta的粉丝");
			break;
		case R.id.ll_publish_mine:
			startPostListActivity("ta的发表",  "topic", "user/topiclist");
			break;
		case R.id.ll_reply_mine:
			startPostListActivity("ta的回复",  "reply", "user/topiclist");
			break;
		default:
			break;
		}
	}

private void startPostListActivity(String title, String type, String r) {
		
		Intent intent = new Intent(getActivity(), ForumPostListActivity.class);
		intent.putExtra(ForumPostListActivity.EXTRA_TITLE, title);
		intent.putExtra(ForumPostListActivity.EXTRA_TYPE, type);
		intent.putExtra(ForumPostListActivity.EXTRA_URL_R, r);
		intent.putExtra(ForumPostListActivity.EXTRA_UID, uid);
		startActivity(intent);
	}
	
	private void startUserListActivity(int type, String title) {

		Intent intent = new Intent(getActivity(), UserListActivity.class);
		intent.putExtra(UserListActivity.EXTRA_TYPE, type);
		intent.putExtra(UserListActivity.EXTRA_TITLE, title);
		intent.putExtra(UserListActivity.EXTRA_UID, uid);
		startActivity(intent);
	}

	private void onClickAlbum() {
		
		Intent intent =new Intent(getActivity(), AlbumActivity.class);
		intent.putExtra(AlbumActivity.EXTRA_UID, uid);
		intent.putExtra(ProfileAcitivity.EXTRA_TITLE, "ta的相册");
		startActivity(AlbumActivity.class);
	}

	private void onClickProfile() {
		
		if(userCenterJson!=null && userCenterJson.body!=null &&userCenterJson.body.profileList!=null && !userCenterJson.body.profileList.isEmpty()) {
			Intent intent = new Intent(getActivity(), ProfileAcitivity.class);
			intent.putExtra(ProfileAcitivity.EXTRA_DATA, userCenterJson.body.profileList);
			intent.putExtra(ProfileAcitivity.EXTRA_TITLE, "ta的资料");
			startActivity(intent);
		} 
	}
	
	
}
