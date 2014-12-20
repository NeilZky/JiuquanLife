package com.jiuquanlife.factory;

import android.content.Context;
import android.graphics.Bitmap;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

public class BitmapUtilsFactory {

	public static BitmapUtils create(Context context) {

		BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils(context
				.getApplicationContext());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		return bitmapUtils;
	}

}
