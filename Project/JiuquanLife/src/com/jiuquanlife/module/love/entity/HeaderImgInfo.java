package com.jiuquanlife.module.love.entity;

public class HeaderImgInfo {
	private String tid;
	private String subject;
	private String img;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "HeaderImgInfo [tid=" + tid + ", subject=" + subject + ", img="
				+ img + "]";
	}

}
