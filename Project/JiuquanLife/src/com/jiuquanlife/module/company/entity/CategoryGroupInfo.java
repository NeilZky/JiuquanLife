package com.jiuquanlife.module.company.entity;

import java.util.List;

public class CategoryGroupInfo extends CategoryInfo {
	private List<CategoryInfo> catInfos;

	public List<CategoryInfo> getCatInfos() {
		return catInfos;
	}

	public void setCatInfos(List<CategoryInfo> catInfos) {
		this.catInfos = catInfos;
	}

	public CategoryGroupInfo(int catid, String catname,
			List<CategoryInfo> catInfos) {
		super(catid, catname);

		this.catInfos = catInfos;
	}

	public CategoryGroupInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
