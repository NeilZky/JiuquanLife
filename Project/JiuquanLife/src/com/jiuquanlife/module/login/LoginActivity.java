package com.jiuquanlife.module.login;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.ToastHelper;

public class LoginActivity extends BaseActivity{

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
	
	private boolean verify() {
		
		if(usernameEt.getText().toString().trim().isEmpty()) {
			ToastHelper.showS("����д�û���");
			return false;
		}
		
		if(pwdEt.getText().toString().isEmpty()) {
			ToastHelper.showS("����д����");
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
						ToastHelper.showL("��½ʧ��");
					}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
				ToastHelper.showL("�������");
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
					finish();
					
				}
			}
		});
	}
	
	
}
