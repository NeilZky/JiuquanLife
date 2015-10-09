package com.jiuquanlife.module.company.entity;

public class ProductInfo {
	private int pid;
	private int uid;
	private int eid;
	private String productName;
	private String discription;
	private String token;
	private String price;
	private String dateline;
	private String company;

	private ImgInfo productImg;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public ImgInfo getProductImg() {
		return productImg;
	}

	public void setProductImg(ImgInfo productImg) {
		this.productImg = productImg;
	}

	@Override
	public String toString() {
		return "ProductInfo [pid=" + pid + ", uid=" + uid + ", eid=" + eid
				+ ", productName=" + productName + ", discription="
				+ discription + ", token=" + token + ", price=" + price
				+ ", dateline=" + dateline + ", company=" + company
				+ ", productImg=" + productImg + "]";
	}

	public ProductInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductInfo(int pid, int uid, int eid, String productName,
			String discription, String token, String price, String dateline,
			String company, ImgInfo productImg) {
		super();
		this.pid = pid;
		this.uid = uid;
		this.eid = eid;
		this.productName = productName;
		this.discription = discription;
		this.token = token;
		this.price = price;
		this.dateline = dateline;
		this.company = company;
		this.productImg = productImg;
	}

}
