package com.jiuquanlife.module.publish.entity;

import java.util.List;

import com.jiuquanlife.module.company.entity.AddressInfo;

public class PublishContent {
	private List<AddressInfo> addressList;
	private List<PublishInfo> infoList;

	@Override
	public String toString() {
		return "PublishContent [addressList=" + addressList + ", infoList="
				+ infoList + "]";
	}

	public List<AddressInfo> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressInfo> addressList) {
		this.addressList = addressList;
	}

	public List<PublishInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<PublishInfo> infoList) {
		this.infoList = infoList;
	}

}
