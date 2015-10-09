package com.jiuquanlife.module.love.entity;

import java.util.List;

public class SearchPageInfo {
	private List<SearchUserInfo> marryMemberList;
	private FilterDatas filterData;

	public FilterDatas getFilterData() {
		return filterData;
	}

	public void setFilterData(FilterDatas filterData) {
		this.filterData = filterData;
	}

	public List<SearchUserInfo> getMarryMemberList() {
		return marryMemberList;
	}

	public void setMarryMemberList(List<SearchUserInfo> marryMemberList) {
		this.marryMemberList = marryMemberList;
	}

	@Override
	public String toString() {
		return "SearchPageInfo [marryMemberList=" + marryMemberList + "]";
	}
}
