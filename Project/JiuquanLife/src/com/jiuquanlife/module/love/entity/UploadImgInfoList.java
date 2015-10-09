package com.jiuquanlife.module.love.entity;

import java.util.List;

public class UploadImgInfoList {
	private List<UploadImgInfos> data;
	private String info;
	private String code;

	public List<UploadImgInfos> getData() {
		return data;
	}

	public void setData(List<UploadImgInfos> data) {
		this.data = data;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
