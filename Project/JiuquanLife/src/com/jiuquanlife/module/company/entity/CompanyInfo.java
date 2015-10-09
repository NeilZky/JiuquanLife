package com.jiuquanlife.module.company.entity;

public class CompanyInfo implements Comparable<CompanyInfo> {
	private String entid;
	private String company;
	private String addressId;
	private String address;
	private int visitNum;
	private String goodCommentRate;
	private int uid;
	private String token;
	private String longitude;
	private String latitude;
	private String addressName;
	private String subAddressName;
	private String commentCount;
	private String starCount;
	private String totalAverage;
	private ImgInfo img;
	private int imgCount;
	private int distance;
	private int isFavorite;

	public String getEntid() {
		return entid;
	}

	public void setEntid(String entid) {
		this.entid = entid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}

	public String getGoodCommentRate() {
		return goodCommentRate;
	}

	public void setGoodCommentRate(String goodCommentRate) {
		this.goodCommentRate = goodCommentRate;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getSubAddressName() {
		return subAddressName;
	}

	public void setSubAddressName(String subAddressName) {
		this.subAddressName = subAddressName;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public String getStarCount() {
		return starCount;
	}

	public void setStarCount(String starCount) {
		this.starCount = starCount;
	}

	public String getTotalAverage() {
		return totalAverage;
	}

	public void setTotalAverage(String totalAverage) {
		this.totalAverage = totalAverage;
	}

	public ImgInfo getImg() {
		return img;
	}

	public void setImg(ImgInfo img) {
		this.img = img;
	}

	public int getImgCount() {
		return imgCount;
	}

	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}

	public int getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}

	public CompanyInfo(String entid, String company, String addressId,
			String address, int visitNum, String goodCommentRate, int uid,
			String token, String longitude, String latitude,
			String addressName, String subAddressName, String commentCount,
			String starCount, String totalAverage, ImgInfo img, int imgCount,
			int distance, int isFavorite) {
		super();
		this.entid = entid;
		this.company = company;
		this.addressId = addressId;
		this.address = address;
		this.visitNum = visitNum;
		this.goodCommentRate = goodCommentRate;
		this.uid = uid;
		this.token = token;
		this.longitude = longitude;
		this.latitude = latitude;
		this.addressName = addressName;
		this.subAddressName = subAddressName;
		this.commentCount = commentCount;
		this.starCount = starCount;
		this.totalAverage = totalAverage;
		this.img = img;
		this.imgCount = imgCount;
		this.distance = distance;
		this.isFavorite = isFavorite;
	}

	@Override
	public String toString() {
		return "CompanyInfo [entid=" + entid + ", company=" + company
				+ ", addressId=" + addressId + ", address=" + address
				+ ", visitNum=" + visitNum + ", goodCommentRate="
				+ goodCommentRate + ", uid=" + uid + ", token=" + token
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", addressName=" + addressName + ", subAddressName="
				+ subAddressName + ", commentCount=" + commentCount
				+ ", starCount=" + starCount + ", totalAverage=" + totalAverage
				+ ", img=" + img + ", imgCount=" + imgCount + ", distance="
				+ distance + ", isFavorite=" + isFavorite + "]";
	}

	public CompanyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(CompanyInfo arg0) {
		// TODO Auto-generated method stub
		return distance - arg0.getDistance();
	}

}
