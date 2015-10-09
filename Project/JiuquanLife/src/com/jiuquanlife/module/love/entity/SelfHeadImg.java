package com.jiuquanlife.module.love.entity;

public class SelfHeadImg {
	private String headsavepath;
	private String headsavename;
	private String maid;

	public String getHeadsavepath() {
		return headsavepath;
	}

	public void setHeadsavepath(String headsavepath) {
		this.headsavepath = headsavepath;
	}

	public String getHeadsavename() {
		return headsavename;
	}

	public void setHeadsavename(String headsavename) {
		this.headsavename = headsavename;
	}

	public String getMaid() {
		return maid;
	}

	public void setMaid(String maid) {
		this.maid = maid;
	}

	@Override
	public String toString() {
		return "SelfHeadImg [headsavepath=" + headsavepath + ", headsavename="
				+ headsavename + ", maid=" + maid + "]";
	}

}
