package com.jiuquanlife.module.love.entity;

public class CommonBaseInfo {
	private String opid;
	private String opname;
	private String opvalue;

	@Override
	public String toString() {
		return opname;
	}

	public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}

	public String getOpvalue() {
		return opvalue;
	}

	public void setOpvalue(String opvalue) {
		this.opvalue = opvalue;
	}


}
