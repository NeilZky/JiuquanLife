package com.jiuquanlife.module.company.entity;

public class IntelligenSortInfo {
	private int id;
	private String intelligentSort;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIntelligentSort() {
		return intelligentSort;
	}

	public void setIntelligentSort(String intelligentSort) {
		this.intelligentSort = intelligentSort;
	}

	public IntelligenSortInfo(int id, String intelligentSort) {
		super();
		this.id = id;
		this.intelligentSort = intelligentSort;
	}

	public IntelligenSortInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "IntelligenSortInfo [id=" + id + ", intelligentSort="
				+ intelligentSort + "]";
	}

}
