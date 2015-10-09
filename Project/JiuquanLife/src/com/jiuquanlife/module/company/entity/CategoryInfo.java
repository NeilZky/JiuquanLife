package com.jiuquanlife.module.company.entity;

public class CategoryInfo {

	private int catid;
	private String catname;

	public CategoryInfo(int catid, String catname) {
		super();
		this.catid = catid;
		this.catname = catname;
	}

	public CategoryInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

}
