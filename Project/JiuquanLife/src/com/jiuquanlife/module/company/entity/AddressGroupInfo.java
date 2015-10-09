package com.jiuquanlife.module.company.entity;

import java.util.List;

public class AddressGroupInfo extends AddressInfo {
	private List<AddressInfo> subAddressList;

	public List<AddressInfo> getSubAddressList() {
		return subAddressList;
	}

	public void setSubAddressList(List<AddressInfo> subAddressList) {
		this.subAddressList = subAddressList;
	}

	@Override
	public String toString() {
		return "AddressGroupInfo [subAddressList=" + subAddressList
				+ ", getAid()=" + getAid() + ", getPid()=" + getPid()
				+ ", getLevel()=" + getLevel() + ", getAddressName()="
				+ getAddressName() + ", toString()=" + super.toString()
				+ ", cloneAddess()=" + cloneAddess() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

	public AddressGroupInfo(int aid, int pid, int level, String addressName,
			List<AddressInfo> subAddressList) {
		super(aid, pid, level, addressName);
		this.subAddressList = subAddressList;
	}

}
