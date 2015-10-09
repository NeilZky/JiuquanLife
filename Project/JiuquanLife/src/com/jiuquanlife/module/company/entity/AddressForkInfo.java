package com.jiuquanlife.module.company.entity;

public class AddressForkInfo {
	private AddressGroupInfo addressList;

	public AddressGroupInfo getAddressList() {
		return addressList;
	}

	public void setAddressList(AddressGroupInfo addressList) {
		this.addressList = addressList;
	}

	@Override
	public String toString() {
		return "AddressForkInfo [addressList=" + addressList + "]";
	}

}
