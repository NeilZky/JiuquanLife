package com.jiuquanlife.entity;

import android.graphics.Bitmap;

public class Photo {

	public Bitmap bitmap;
	public String filePath;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
