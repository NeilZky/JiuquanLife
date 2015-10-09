package com.jiuquanlife.module.love.entity;

import java.util.List;

public class SelfImgInfos {
	private SelfHeadImg headPhoto;
	private List<LifeImg> lifePhotoList;

	public SelfHeadImg getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(SelfHeadImg headPhoto) {
		this.headPhoto = headPhoto;
	}

	public List<LifeImg> getLifePhotoList() {
		return lifePhotoList;
	}

	public void setLifePhotoList(List<LifeImg> lifePhotoList) {
		this.lifePhotoList = lifePhotoList;
	}

	@Override
	public String toString() {
		return "SelfImgInfos [headPhoto=" + headPhoto + ", lifePhotoList="
				+ lifePhotoList + "]";
	}

}
