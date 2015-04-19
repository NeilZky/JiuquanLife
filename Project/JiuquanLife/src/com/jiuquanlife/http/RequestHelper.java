package com.jiuquanlife.http;

import java.lang.reflect.Field;
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
						
						ToastHelper.showL("�������");

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
	
	public void postRequestMap(Context context,String url, final Map<String, String> params, final  Response.Listener<String> listener) {
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest sRequest = new StringRequest(Request.Method.POST,
				url, listener, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						
						ToastHelper.showL("�������");

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				
				return params;
			}

		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}
	
	public void postRequest(Context context,String url, final Map<String, String> params, final  Response.Listener<String> listener, final Response.ErrorListener onError) {
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest sRequest = new StringRequest(Request.Method.POST,
				url, listener, onError) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				
				return params;
			}

		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}
	
	public void postRequestEntity(Context context,String url, Object entity, final Response.Listener<String> listener) {
		
		final Map<String, String> params = convertObjToMap(entity);
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest sRequest = new StringRequest(Request.Method.POST,
				url, listener,  new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						
						ToastHelper.showL("�������");

					}
				} ) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				
				return params;
			}

		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}
	
	public void postRequestEntity(Context context,String url, Object entity, final  Response.Listener<String> listener, final Response.ErrorListener onError) {
		
		final Map<String, String> params = convertObjToMap(entity);
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest sRequest = new StringRequest(Request.Method.POST,
				url, listener, onError) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				
				return params;
			}

		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}
	
	public void getRequest(Context context,String url, final  Response.Listener<String> listener) {
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest sRequest = new StringRequest(Request.Method.GET,
				url, listener, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						
						ToastHelper.showL("�������");

					}
				}) {
		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}

	private HashMap<String, String> convertObjToMap(Object input) {
		
		Field[] fields = input.getClass().getFields();
		if(fields!=null) {
			HashMap<String, String> res = new HashMap<String, String>();
			for(Field field : fields) {
				String key = field.getName();
				field.setAccessible(true);
				 try {
					Object value = field.get(input);
					if(value!=null) {
						String hashValue = value.toString();
						res.put(key, hashValue);
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return res;
		}
		return null;
	}
	
	
}
