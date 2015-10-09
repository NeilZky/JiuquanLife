package com.jiuquanlife.module.love.entity;

import java.util.List;

public class FilterDatas {
	private List<FilterInfo> genderList;
	private List<FilterInfo> ageList;
	private List<FilterInfo> statureList;
	private List<FilterInfo> eduList;
	private List<FilterInfo> addressList;

	public List<FilterInfo> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<FilterInfo> addressList) {
		this.addressList = addressList;
	}

	public String[] addressArray() {
		return convertListToArray(addressList);
	}

	public List<FilterInfo> getGenderList() {
		return genderList;
	}

	public void setGenderList(List<FilterInfo> genderList) {
		this.genderList = genderList;
	}

	public String[] genderArray() {
		return convertListToArray(genderList);
	}

	public List<FilterInfo> getAgeList() {
		return ageList;
	}

	public void setAgeList(List<FilterInfo> ageList) {
		this.ageList = ageList;
	}

	public String[] ageArray() {
		return convertListToArray(ageList);
	}

	public List<FilterInfo> getStatureList() {
		return statureList;
	}

	public void setStatureList(List<FilterInfo> statureList) {
		this.statureList = statureList;
	}

	public String[] statureArray() {
		return convertListToArray(statureList);
	}

	public List<FilterInfo> getEduList() {
		return eduList;
	}

	public void setEduList(List<FilterInfo> eduList) {
		this.eduList = eduList;
	}

	public String[] eduArray() {
		return convertListToArray(eduList);
	}

	@Override
	public String toString() {
		return "FilterDatas [genderList=" + genderList + ", ageList=" + ageList
				+ ", statureList=" + statureList + ", eduList=" + eduList
				+ ", getGenderList()=" + getGenderList() + ", getAgeList()="
				+ getAgeList() + ", getStatureList()=" + getStatureList()
				+ ", getEduList()=" + getEduList() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public static String[] convertListToArray(List<FilterInfo> infos) {
		if (null == infos) {
			return null;
		}

		String[] arrays = new String[infos.size()];
		for (int i = 0; i < infos.size(); i++) {
			FilterInfo info = infos.get(i);
			arrays[i] = info.getName();
		}
		return arrays;

	}

}
