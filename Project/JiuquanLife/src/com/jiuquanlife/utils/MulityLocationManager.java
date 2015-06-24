package com.jiuquanlife.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

public class MulityLocationManager {

	public static final int LOC_TYPE_BAIDU = 1;
	public static final int LOC_TYPE_GAODE = 0;
	public static final int LOC_TYPE_TENCENT = 2;
	
	private static final int MAX_RADIUS_DISTANCE = 2000;
	private Context context;
	private LocationManagerProxy glm;// 高德定位
	private Handler handler = new Handler();

	private OnLocationChangedListener onLocationChangedListener;

	public MulityLocationManager(Context context) {
		this.context = context;

	}

	public static MulityLocationManager getInstance(Context context) {

		return new MulityLocationManager(context);
	}

	public void requestLocation() {

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				stopRequestAddr();
			}
		}, 2 * 60 * 1000L);
		requestAddrByGD();

	}

	public void stopRequestAddr() {

		if (glm != null) {
			glm.removeUpdates(amapLocationListener);
			glm.destroy();
		}
	}



	private void requestAddrByGD() {

		if (glm == null) {
			glm = LocationManagerProxy.getInstance(context);
		}
		glm.requestLocationData(LocationProviderProxy.AMapNetwork, 6000L, 100,
				amapLocationListener);
	}

	private AMapLocationListener amapLocationListener = new AMapLocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			
			if (amapLocation != null&& amapLocation.getAccuracy()<=MAX_RADIUS_DISTANCE && amapLocation.getLongitude() > 1) {
				glm.removeUpdates(amapLocationListener);
				double longitude = amapLocation.getLongitude();
				double latitude = amapLocation.getLatitude();
				String address = amapLocation.getAddress();
				float accyarcy = amapLocation.getAccuracy();
				double bdLon = LocationUtils
						.convertToBdLon(latitude, longitude);
				double bdLat = LocationUtils
						.convertToBdLat(latitude, longitude);
				onLocationChangedListener.onLocationChanged(bdLat, bdLon,
						accyarcy, address);
			} 
		}
	};

	public void setOnLocationChangedListener(
			OnLocationChangedListener onLocationChangedListener) {
		this.onLocationChangedListener = onLocationChangedListener;
	}

	public interface OnLocationChangedListener {

		public void onLocationChanged(double latitude, double longitude,
				double accyarcy, String addr);
	}
}
