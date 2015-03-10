package com.jiuquanlife.utils;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;

public class BitmapHelper {
	private BitmapHelper() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils(Context appContext) {
		bitmapUtils = new BitmapUtils(appContext);
		return bitmapUtils;
	}
}
