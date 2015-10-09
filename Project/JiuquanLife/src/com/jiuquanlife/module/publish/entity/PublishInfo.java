package com.jiuquanlife.module.publish.entity;

import java.util.ArrayList;
import java.util.List;

import com.jiuquanlife.module.company.entity.ImgInfo;

public class PublishInfo {
	private int infoId;
	private int infoTypeId;
	private int uid;
	private String infoTitle;
	private String content;
	private String type;
	private String telNum;
	private String dateline;
	private String infoTypeCss;
	private String userName;
	private ArrayList<ImgInfo> coverPics;

	@Override
	public String toString() {
		return "PublishInfo [infoId=" + infoId + ", infoTypeId=" + infoTypeId
				+ ", uid=" + uid + ", infoTitle=" + infoTitle + ", content="
				+ content + ", type=" + type + ", telNum=" + telNum
				+ ", dateline=" + dateline + ", infoTypeCss=" + infoTypeCss
				+ ", userName=" + userName + ", coverPics=" + coverPics + "]";
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public int getInfoTypeId() {
		return infoTypeId;
	}

	public void setInfoTypeId(int infoTypeId) {
		this.infoTypeId = infoTypeId;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getInfoTypeCss() {
		return infoTypeCss;
	}

	public void setInfoTypeCss(String infoTypeCss) {
		this.infoTypeCss = infoTypeCss;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<ImgInfo> getCoverPics() {
		return coverPics;
	}

	public void setCoverPics(ArrayList<ImgInfo> coverPics) {
		this.coverPics = coverPics;
	}

}
