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
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.ToastHelper;

public class RegisterActivity extends BaseActivity{


	private EditText usernameEt;
	private EditText pwdEt;
	private EditText emailEt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		initViews();
	}

	private void initViews() {

		setContentView(R.layout.activity_register);
		usernameEt = (EditText) findViewById(R.id.et_username_login_activity);
		pwdEt = (EditText) findViewById(R.id.et_pwd_login_activity);
		emailEt = (EditText) findViewById(R.id.et_email_login_activity);
	}
	
	public void onClickRegister(View v) {
		
		if(verify()) {
			register();
		}
	}
	
	private boolean verify() {
		
		if(usernameEt.getText().toString().trim().isEmpty()) {
			ToastHelper.showS("«ÎÃÓ–¥”√ªß√˚");
			return false;
		}
		
		if(pwdEt.getText().toString().isEmpty()) {
			ToastHelper.showS("«ÎÃÓ–¥√‹¬Î");
			return false;
		}
		
		if(emailEt.getText().toString().isEmpty()) {
			ToastHelper.showS("«ÎÃÓ–¥” œ‰");
			return false;
		}
		return true;
	}
	
	private void register() {
		
		final String username = usernameEt.getText().toString().trim();
		final String password = pwdEt.getText().toString();
		String email = emailEt.getText().toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		map.put("email", email);
		showProgressDialog();
		RequestHelper.getInstance().postRequest(this, UrlConstance.REGISTER, map, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
					try {
						JSONObject json = new JSONObject(response);
						int rs = json.getInt("rs");
						if(rs == 1) {
							ToastHelper.showL("◊¢≤·≥…π¶");
							Intent data = new Intent();
							data.putExtra(ExtraConstance.NAME, username);
							data.putExtra(ExtraConstance.PWD, password);
							setResult(Activity.RESULT_OK, data);
							finish();
						} else {
							ToastHelper.showL(json.getString("errcode"));
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
						ToastHelper.showL("◊¢≤· ß∞‹");
					}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
				ToastHelper.showL("Õ¯¬Á¥ÌŒÛ");
			}
		});
	}
	
}
