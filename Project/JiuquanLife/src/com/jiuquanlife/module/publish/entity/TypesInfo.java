package com.jiuquanlife.module.publish.entity;

import java.util.List;

public class TypesInfo {
	private List<TypeGroupInfo> data;

	public List<TypeGroupInfo> getTypeInfos() {
		return data;
	}

	public void setTypeInfos(List<TypeGroupInfo> typeInfos) {
		this.data = typeInfos;
	}

	@Override
	public String toString() {
		return "TypesInfo [typeInfos=" + data + "]";
	}

}
