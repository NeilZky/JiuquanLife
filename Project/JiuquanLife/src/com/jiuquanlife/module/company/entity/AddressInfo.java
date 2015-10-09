package com.jiuquanlife.module.company.entity;

public class AddressInfo {
	private int aid;
	private int pid;
	private int level;
	private String addressName;

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	@Override
	public String toString() {
		return addressName;
	}

	public AddressInfo(int aid, int pid, int level, String addressName) {
		super();
		this.aid = aid;
		this.pid = pid;
		this.level = level;
		this.addressName = addressName;
	}

	public AddressInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressInfo cloneAddess() {
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setLevel(level);
		addressInfo.setPid(pid);

		return addressInfo;
	}
}
