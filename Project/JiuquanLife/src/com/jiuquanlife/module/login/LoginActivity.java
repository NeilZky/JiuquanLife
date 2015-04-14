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
							finish();
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
	
}
