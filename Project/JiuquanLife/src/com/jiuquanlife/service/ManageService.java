package com.jiuquanlife.service;

import android.content.Context;

import com.android.volley.Response;
import com.jiuquanlife.http.ManageMethodName;
import com.jiuquanlife.http.ModuleName;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.http.UrlConfig;

public class ManageService {

	private static ManageService instance;
	private static Object lock = new Object();
	private String BASE_URL_PREFIX = UrlConfig.URL_REFPIX + ModuleName.MANAGE; 
	private RequestHelper requestHelper = RequestHelper.getInstance();
	
	private ManageService() {
		
	}

	public static ManageService getInstance() {
		if (null == instance) {
			synchronized (lock) {
				if (null == instance) {
					instance = new ManageService();
				}
			}
		}
		return instance;
	}
	
	private void postRequest(Context context, String methodName,Object jsonObj, final Response.Listener<String> listener) {
		
		requestHelper.postRequest(context, BASE_URL_PREFIX + methodName, jsonObj, listener);
	}
	
	public  void login(Context context, Object jsonObj, final Response.Listener<String> listener) {
		
		postRequest(context, ManageMethodName.LOGIN, jsonObj, listener);
	}
}
