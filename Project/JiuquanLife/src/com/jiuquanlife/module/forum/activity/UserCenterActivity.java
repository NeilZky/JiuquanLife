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

public class UserCenterActivity extends BaseActivity{
	
	private CircleImageView civ_photo_user_center;
	private UserCenterJson userCenterJson;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		
		initViews();
		getData();
	}

	private void initViews() {
	
		setContentView(R.layout.activity_user_center);
		civ_photo_user_center = (CircleImageView) findViewById(R.id.civ_photo_user_center);
	}
	
	
	public void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userinfo");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("userId", user.uid + "");
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
		ImageLoader.getInstance().displayImage(json.icon, civ_photo_user_center);
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
			onClickProfileMine();
			break;
		case R.id.ll_album_mine:
			onClickAlbum();
			break;
		case R.id.ll_friends_mine:
			startUserListActivity( UserListActivity.TYPE_FIREND, "我的好友");
			break;
		case R.id.ll_follow_mine:
			startUserListActivity( UserListActivity.TYPE_FOLLOW, "我的关注");
			break;
		case R.id.ll_followed_mine:
			startUserListActivity( UserListActivity.TYPE_FOLLOWED , "我的粉丝");
			break;
		case R.id.ll_publish_mine:
			startPostListActivity("我的发表",  "topic", "user/topiclist");
			break;
		case R.id.ll_reply_mine:
			startPostListActivity("我的回复",  "reply", "user/topiclist");
			break;
		case R.id.ll_favorite_mine:
			startPostListActivity("我的收藏",  "favorite", "user/topiclist");
			break;
		default:
			break;
		}
	}
	
	private void startPostListActivity(String title, String type, String r) {
		
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		Intent intent = new Intent(getActivity(), ForumPostListActivity.class);
		intent.putExtra(ForumPostListActivity.EXTRA_TITLE, title);
		intent.putExtra(ForumPostListActivity.EXTRA_TYPE, type);
		intent.putExtra(ForumPostListActivity.EXTRA_URL_R, r);
		intent.putExtra(ForumPostListActivity.EXTRA_UID, user.uid);
		startActivity(intent);
	}

	private void startUserListActivity(int type, String title) {

		Intent intent = new Intent(getActivity(), UserListActivity.class);
		intent.putExtra(UserListActivity.EXTRA_TYPE, type);
		intent.putExtra(UserListActivity.EXTRA_TITLE, title);
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		intent.putExtra(UserListActivity.EXTRA_UID, user.uid);
		startActivity(intent);
	}

	private void onClickAlbum() {
		
		Intent intent =new Intent(getActivity(), AlbumActivity.class);
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		intent.putExtra(AlbumActivity.EXTRA_UID, user.uid);
		startActivity(intent);
	}

	private void onClickProfileMine() {
		
		if(userCenterJson!=null && userCenterJson.body!=null &&userCenterJson.body.profileList!=null && !userCenterJson.body.profileList.isEmpty()) {
			Intent intent = new Intent(getActivity(), ProfileAcitivity.class);
			intent.putExtra(ProfileAcitivity.EXTRA_DATA, userCenterJson.body.profileList);
			startActivity(intent);
		} 
	}
	
	
}
