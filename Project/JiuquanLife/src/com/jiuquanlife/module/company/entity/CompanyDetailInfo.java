package com.jiuquanlife.module.company.entity;

import java.util.List;

public class CompanyDetailInfo {
	private int endtid;
	private String company;
	private int addressId;
	private String address;
	private int type;
	private String tel;
	private String qq;
	private String email;
	private String site;
	private int visitNum;
	private String intro;
	private String goodCommentRate;
	private String pic;
	private String token;
	private ImgInfo logoImg;

	private List<CommentInfo> commentList;
	private List<ProductInfo> productList;
	private List<ImgInfo> envirenmentImg;

	public int getEndtid() {
		return endtid;
	}

	public void setEndtid(int endtid) {
		this.endtid = endtid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getGoodCommentRate() {
		return goodCommentRate;
	}

	public void setGoodCommentRate(String goodCommentRate) {
		this.goodCommentRate = goodCommentRate;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public ImgInfo getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(ImgInfo logoImg) {
		this.logoImg = logoImg;
	}

	public List<CommentInfo> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentInfo> commentList) {
		this.commentList = commentList;
	}

	@Override
	public String toString() {
		return "CompanyDetailInfo [endtid=" + endtid + ", company=" + company
				+ ", addressId=" + addressId + ", address=" + address
				+ ", type=" + type + ", tel=" + tel + ", qq=" + qq + ", email="
				+ email + ", site=" + site + ", visitNum=" + visitNum
				+ ", intro=" + intro + ", goodCommentRate=" + goodCommentRate
				+ ", pic=" + pic + ", token=" + token + ", logoImg=" + logoImg
				+ ", commentList=" + commentList + ", productList="
				+ productList + ", envirenmentImg=" + envirenmentImg + "]";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<ProductInfo> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductInfo> productList) {
		this.productList = productList;
	}

	public List<ImgInfo> getEnvirenmentImg() {
		return envirenmentImg;
	}

	public void setEnvirenmentImg(List<ImgInfo> envirenmentImg) {
		this.envirenmentImg = envirenmentImg;
	}

}
