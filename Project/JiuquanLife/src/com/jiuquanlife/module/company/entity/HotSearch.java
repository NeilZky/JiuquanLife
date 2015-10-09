package com.jiuquanlife.module.company.entity;

import java.util.List;

public class HotSearch {
	private List<String> data;
	private String code;

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "HotSearch [data=" + data + ", code=" + code + "]";
	}

}
