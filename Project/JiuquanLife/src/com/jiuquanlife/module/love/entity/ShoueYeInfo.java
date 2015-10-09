package com.jiuquanlife.module.love.entity;

import java.util.List;

public class ShoueYeInfo {
	private List<HeaderImgInfo> MarryImgs;
	private List<LoveInfo> MarryPost;

	public List<HeaderImgInfo> getMarryImgs() {
		return MarryImgs;
	}

	public void setMarryImgs(List<HeaderImgInfo> marryImgs) {
		MarryImgs = marryImgs;
	}

	public List<LoveInfo> getMarryPost() {
		return MarryPost;
	}

	public void setMarryPost(List<LoveInfo> marryPost) {
		MarryPost = marryPost;
	}

	@Override
	public String toString() {
		return "ShoueYeInfo [MarryImgs=" + MarryImgs + ", MarryPost="
				+ MarryPost + "]";
	}

}
