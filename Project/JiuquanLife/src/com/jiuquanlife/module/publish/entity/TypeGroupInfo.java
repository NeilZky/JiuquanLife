package com.jiuquanlife.module.publish.entity;

import java.util.List;

public class TypeGroupInfo extends TypeInfo {
	private List<TypeInfo> subInfoTypeList;

	public List<TypeInfo> getSubInfoTypeList() {
		return subInfoTypeList;
	}

	public void setSubInfoTypeList(List<TypeInfo> subInfoTypeList) {
		this.subInfoTypeList = subInfoTypeList;
	}

	@Override
	public String toString() {
		return getTypeName();
	}

}
