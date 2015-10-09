package com.jiuquanlife.module.love.entity;

import java.util.List;

import android.text.TextUtils;

public class SearchUserInfo {
	private String maid;
	private String uid;
	private String nickname;
	private String sex;
	private String birthday;
	private String location;
	private String edu;
	private String stature;
	private String headsavepath;
	private String headsavename;
	private String viewnum;
	private String dateline;
	private String lastLoginTime;
	private String distance;
	private String zanCount;
	private String hasMyZan;
	private String hasFavorite;
	private String isFriend;
	private String hasHi;
	private String loveglow;
	private String resume;
	private String telphone;
	private String verifyStatus;
	private String mateselection;
	private String emailVerifyStatus;
	private String starVerifyStatus;
	private String mobileVerifyStatus;
	private List<String> hobby;
	private List<LifeImg> lifePhotos;

	public List<LifeImg> getLifePhotos() {
		return lifePhotos;
	}

	public void setLifePhotos(List<LifeImg> lifePhotos) {
		this.lifePhotos = lifePhotos;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMateselection() {
		return mateselection;
	}

	public void setMateselection(String mateselection) {
		this.mateselection = mateselection;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getStature() {
		return stature;
	}

	public void setStature(String stature) {
		this.stature = stature;
	}

	public String getHeadsavepath() {
		return headsavepath;
	}

	public void setHeadsavepath(String headsavepath) {
		this.headsavepath = headsavepath;
	}

	public String getHeadsavename() {
		return headsavename;
	}

	public void setHeadsavename(String headsavename) {
		this.headsavename = headsavename;
	}

	public String getViewnum() {
		return viewnum;
	}

	public void setViewnum(String viewnum) {
		this.viewnum = viewnum;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getHasMyZan() {
		return hasMyZan;
	}

	public void setHasMyZan(String hasMyZan) {
		this.hasMyZan = hasMyZan;
	}

	public String getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(String isFriend) {
		this.isFriend = isFriend;
	}

	public String getHasHi() {
		return hasHi;
	}

	public void setHasHi(String hasHi) {
		this.hasHi = hasHi;
	}

	@Override
	public String toString() {
		return "SearchUserInfo [maid=" + maid + ", uid=" + uid + ", nickname="
				+ nickname + ", sex=" + sex + ", birthday=" + birthday
				+ ", location=" + location + ", edu=" + edu + ", stature="
				+ stature + ", headsavepath=" + headsavepath
				+ ", headsavename=" + headsavename + ", viewnum=" + viewnum
				+ ", dateline=" + dateline + ", lastLoginTime=" + lastLoginTime
				+ ", distance=" + distance + ", zanCount=" + zanCount
				+ ", hasMyZan=" + hasMyZan + ", hasFavorite=" + hasFavorite
				+ ", isFriend=" + isFriend + ", hasHi=" + hasHi + ", loveglow="
				+ loveglow + ", resume=" + resume + ", telphone=" + telphone
				+ ", verifyStatus=" + verifyStatus + ", mateselection="
				+ mateselection + ", emailVerifyStatus=" + emailVerifyStatus
				+ ", starVerifyStatus=" + starVerifyStatus
				+ ", mobileVerifyStatus=" + mobileVerifyStatus + ", hobby="
				+ hobby + ", lifePhotos=" + lifePhotos + "]";
	}

	public String getHasFavorite() {
		return hasFavorite;
	}

	public void setHasFavorite(String hasFavorite) {
		this.hasFavorite = hasFavorite;
	}

	public String getZanNum() {
		return zanCount;
	}

	public void setZanNum(String zanNum) {
		this.zanCount = zanNum;
	}

	public String getLoveglow() {
		return loveglow;
	}

	public void setLoveglow(String loveglow) {
		this.loveglow = loveglow;
	}

	public List<String> getHobby() {
		return hobby;
	}

	public void setHobby(List<String> hobby) {
		this.hobby = hobby;
	}

	public String getHobbyValue() {
		if (null == hobby) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String value : hobby) {
			sb.append(value + " ");
		}
		return sb.toString();
	}

	public String getMaid() {
		return maid;
	}

	public void setMaid(String maid) {
		this.maid = maid;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getZanCount() {
		return zanCount;
	}

	public void setZanCount(String zanCount) {
		this.zanCount = zanCount;
	}

	public String getEmailVerifyStatus() {
		return emailVerifyStatus;
	}

	public void setEmailVerifyStatus(String emailVerifyStatus) {
		this.emailVerifyStatus = emailVerifyStatus;
	}

	public String getStarVerifyStatus() {
		return starVerifyStatus;
	}

	public void setStarVerifyStatus(String starVerifyStatus) {
		this.starVerifyStatus = starVerifyStatus;
	}

	public String getMobileVerifyStatus() {
		return mobileVerifyStatus;
	}

	public void setMobileVerifyStatus(String mobileVerifyStatus) {
		this.mobileVerifyStatus = mobileVerifyStatus;
	}

}
