package com.jiuquanlife.http;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jiuquanlife.service.ManageService;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.ToastHelper;

public class RequestHelper {
	
	private static RequestHelper instance;
	private static Object lock = new Object();
	
	private RequestHelper() {
		
	}
	
	public static RequestHelper getInstance() {
		if (null == instance) {
			synchronized (lock) {
				if (null == instance) {
					instance = new RequestHelper();
				}
			}
		}
		return instance;
	}
	
	
	public void postRequest(Context context,String url, final Object jsonObj, final  Response.Listener<String> listener) {
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest sRequest = new StringRequest(Request.Method.POST,
				url, listener, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						
						ToastHelper.showL("ÍøÂç´íÎó");

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("input", GsonUtils.toJson(jsonObj));
				return params;
			}

		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}
	
}
