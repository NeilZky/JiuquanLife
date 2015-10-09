package com.jiuquanlife.module.login;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.ExtraConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.dao.UserDao;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.im.RongCloudBll;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.vo.forum.usercenter.UserInfoJson;

public class LoginActivity extends BaseActivity{

	private static final int REQUEST_REGISTER = 1;
	private EditText usernameEt;
	private EditText pwdEt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		initViews();
	}

	private void initViews() {

		setContentView(R.layout.activity_login);
		usernameEt = (EditText) findViewById(R.id.et_username_login_activity);
		pwdEt = (EditText) findViewById(R.id.et_pwd_login_activity);
	}
	
	public void onClickLogin(View v) {
		
		if(verify()) {
			login();
		}
	}
	
	public void onClickRegister(View v) {
		
		Intent intent = new Intent(getActivity(), RegisterActivity.class);
		startActivityForResult(intent, REQUEST_REGISTER);
	}
	
	
	private boolean verify() {
		
		if(usernameEt.getText().toString().trim().isEmpty()) {
			ToastHelper.showS("ÇëÌîÐ´ÓÃ»§Ãû");
			return false;
		}
		
		if(pwdEt.getText().toString().isEmpty()) {
			ToastHelper.showS("ÇëÌîÐ´ÃÜÂë");
			return false;
		}
		return true;
	}
	
	private void login() {
		
		String username = usernameEt.getText().toString().trim();
		String password = pwdEt.getText().toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		map.put("type", "login");
		showProgressDialog();
		RequestHelper.getInstance().postRequest(this, UrlConstance.LOGIN, map, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
					try {
						JSONObject json = new JSONObject(response);
						int uid = json.getInt("uid");
						String token = json.getString("token");
						String secret = json.getString("secret");
						String userName = json.getString("userName");
						String photoUrl = json.getString("avatar");
						if(uid!=0) {
							User user = new User();
							user.token = token;
							user.uid = uid;
							user.secret = secret;
							user.userName = userName;
							user.photoUrl = photoUrl;
							
							SharePreferenceUtils.putObject(SharePreferenceUtils.USER, user);
							setResult(Activity.RESULT_OK);
							getRongyunToken();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						ToastHelper.showL("µÇÂ½Ê§°Ü");
					}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
				ToastHelper.showL("ÍøÂç´íÎó");
			}
		});
	}
	
	
	private void getRongyunToken() {
		
		User user =  SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", user.uid + "");
		map.put("username", user.userName);
		RequestHelper.getInstance().getRequestMap(this, UrlConstance.GET_RONGYUN_TOKEN, map, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				dismissProgressDialog();
				if(response!=null) {
					String token  = null;
					try {
						JSONObject json = new JSONObject(response);
						token = json.getJSONObject("data").getString("token");
						getUserData();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					try {
						JSONObject json = new JSONObject(response);
						if(token == null) {
							token = json.getString("data");
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SharePreferenceUtils.setValue(SharePreferenceUtils.RONGYUN_TOKEN, token);
					getUserData();
					RongCloudBll.getInstance().connectRongCloud(null);
					finish();
					
				}
			}
		});
	}
	
	private void getUserData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userlist");
		map.put("page", "1");
		map.put("pageSize", 200000+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid + "");
		map.put("orderBy", "dateline");
		map.put("type", "friend");
		String postUrl = UrlConstance.FORUM_URL;
		RequestHelper.getInstance().getRequestMap(getActivity(),
				postUrl, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						UserInfoJson json = GsonUtils.toObj(response, UserInfoJson.class);
						if(json!=null && json.list!=null && !json.list.isEmpty()) {
							new UserDao().save(json.list);
						}
						
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
					}
				});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_REGISTER && resultCode == Activity.RESULT_OK) {
			String name = data.getStringExtra(ExtraConstance.NAME);
			String pwd = data.getStringExtra(ExtraConstance.PWD);
			TextViewUtils.setText(usernameEt, name);
			TextViewUtils.setText(pwdEt, pwd);
		}
	}
}
