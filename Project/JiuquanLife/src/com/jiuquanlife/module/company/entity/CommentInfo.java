package com.jiuquanlife.module.company.entity;

import java.util.List;

public class CommentInfo {
	private int id;
	private String uid;
	private int entid;
	private String dateline;
	private String message;
	private int evaluateTotal;
	private int evaluateTaste;
	private int evaluateService;
	private String createdate;
	private List<ImgInfo> img;
	private int myCommentCount;
	private String userName;

	public int getMyCommentCount() {
		return myCommentCount;
	}

	public void setMyCommentCount(int myCommentCount) {
		this.myCommentCount = myCommentCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEntid() {
		return entid;
	}

	public void setEntid(int entid) {
		this.entid = entid;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getEvaluateTotal() {
		return evaluateTotal;
	}

	public void setEvaluateTotal(int evaluateTotal) {
		this.evaluateTotal = evaluateTotal;
	}

	public int getEvaluateTaste() {
		return evaluateTaste;
	}

	public void setEvaluateTaste(int evaluateTaste) {
		this.evaluateTaste = evaluateTaste;
	}

	public int getEvaluateService() {
		return evaluateService;
	}

	public void setEvaluateService(int evaluateService) {
		this.evaluateService = evaluateService;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public List<ImgInfo> getImg() {
		return img;
	}

	public void setImg(List<ImgInfo> img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "CommentInfo [id=" + id + ", uid=" + uid + ", entid=" + entid
				+ ", dateline=" + dateline + ", message=" + message
				+ ", evaluateTotal=" + evaluateTotal + ", evaluateTaste="
				+ evaluateTaste + ", evaluateService=" + evaluateService
				+ ", createdate=" + createdate + ", img=" + img
				+ ", myCommentCount=" + myCommentCount + ", userName="
				+ userName + "]";
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
