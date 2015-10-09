package com.jiuquanlife.module.publish.entity;

import java.util.List;

import com.jiuquanlife.module.company.entity.AddressInfo;

public class NewInfo {
	private List<AddressInfo> addressList;
	private List<TypeGroupInfo> typeList;
	private String token;

	public List<AddressInfo> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressInfo> addressList) {
		this.addressList = addressList;
	}

	public List<TypeGroupInfo> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TypeGroupInfo> typeList) {
		this.typeList = typeList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "NewInfo [addressList=" + addressList + ", typeList=" + typeList
				+ ", token=" + token + "]";
	}

}
