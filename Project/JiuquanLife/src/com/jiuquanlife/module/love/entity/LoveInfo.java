package com.jiuquanlife.module.love.entity;

import java.util.List;

public class LoveInfo {
	private String fid;
	private String tid;
	private String author;
	private String authorid;
	private String subject;
	private String dateline;
	private String views;
	private String replies;
	private String fName;
	private List<String> imgList;
	private String title;
	private String gender;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getAutor() {
		return author;
	}

	public void setAutor(String autor) {
		this.author = autor;
	}

	public String getAuthorid() {
		return authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public String getReplies() {
		return replies;
	}

	public void setReplies(String replies) {
		this.replies = replies;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	@Override
	public String toString() {
		return "LoveInfo [fid=" + fid + ", tid=" + tid + ", author=" + author
				+ ", authorid=" + authorid + ", subject=" + subject
				+ ", dateline=" + dateline + ", views=" + views + ", replies="
				+ replies + ", fName=" + fName + ", imgList=" + imgList
				+ ", title=" + title + ", gender=" + gender + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
