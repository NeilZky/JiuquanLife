package com.jiuquanlife.module.love.entity;

public class LifeImg {
	private String savepath;
	private String savename;
	private String phid;
	private String marryid;

	public String getSavepath() {
		return savepath;
	}

	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}

	public String getSavename() {
		return savename;
	}

	public void setSavename(String savename) {
		this.savename = savename;
	}

	public String getPhid() {
		return phid;
	}

	public void setPhid(String phid) {
		this.phid = phid;
	}

	@Override
	public String toString() {
		return "LifeImg [savepath=" + savepath + ", savename=" + savename
				+ ", phid=" + phid + ", marryid=" + marryid + "]";
	}

}
