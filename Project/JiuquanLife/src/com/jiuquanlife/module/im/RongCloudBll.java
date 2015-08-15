package com.jiuquanlife.module.im;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import android.os.Handler;

import com.jiuquanlife.app.RongCloudEvent;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.StringUtils;
import com.jiuquanlife.utils.ToastHelper;


public class RongCloudBll {
	
	private Handler handler = new Handler();
	private static RongCloudBll instance = null;
	private static Object lock = new Object();
	private boolean connectSuccess = false;
	
	public static RongCloudBll getInstance() {

		if (null == instance) {
			synchronized (lock) {
				if (null == instance) {
					instance = new RongCloudBll();
				}
			}
		}
		return instance;
	}
	
	public void reconnectRongCloud() {
		
		reconnectRongCloud(new RongIMClient.ConnectCallback() {

				@Override
				public void onTokenIncorrect() {
					
					ToastHelper.showL("�Ự���������������֤ʧ��");
				}

				@Override
				public void onSuccess(String userId) {
					
					connectSuccess = true;
					RongCloudEvent.getInstance().setOtherListener();
					ToastHelper.showL("���ӻỰ�������ɹ�");
				}

				@Override
				public void onError(RongIMClient.ErrorCode e) {
					
					ToastHelper.showL("���ӻỰ����������");
				}
			});
	}
	
	public void reconnectRongCloud(RongIMClient.ConnectCallback callBack) {
		
		connectSuccess = false;
		connectRongCloud(callBack);
	}
	
	public void connectRongCloud(final RongIMClient.ConnectCallback callBack) {
		
		String token = SharePreferenceUtils.getValue(SharePreferenceUtils.RONGYUN_TOKEN, null);
		if (StringUtils.isNullOrEmpty(token)) {
			return;
		}
		
		if(connectSuccess) {
			return;
		} 
		
		//����ʧ�ܾ�15s����������
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				connectRongCloud(callBack);
			}
		}, 15000L);
		
		try {
			RongIM.connect(token, new ConnectCallback() {
				
				@Override
				public void onSuccess(String arg0) {
					connectSuccess = true;
					RongCloudEvent.getInstance().setOtherListener();
					ToastHelper.showL("���ӻỰ�������ɹ�");
					if(callBack!=null) {
						callBack.onSuccess(arg0);
						
					}
				}
				
				@Override
				public void onError(ErrorCode arg0) {
					
					ToastHelper.showL("���ӻỰ����������");
					if(callBack!=null) {
						callBack.onError(arg0);
						
					}
				}
				
				@Override
				public void onTokenIncorrect() {
					ToastHelper.showL("�Ự���������������֤ʧ��");
					if(callBack!=null) {
						callBack.onTokenIncorrect();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
}
