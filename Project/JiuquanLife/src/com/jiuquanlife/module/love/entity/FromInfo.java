package com.jiuquanlife.module.love.entity;

public class FromInfo {
	private String uid;
	private String nickname;
	private String birthday;
	private String location;
	private String edu;
	private String stature;
	private String headsavepath;
	private String headsavename;
	private String age;
	private String maid;

	public String getStature() {
		return stature;
	}

	public void setStature(String stature) {
		this.stature = stature;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "FromInfo [uid=" + uid + ", nickname=" + nickname
				+ ", birthday=" + birthday + ", location=" + location
				+ ", edu=" + edu + ", stature=" + stature + ", headsavepath="
				+ headsavepath + ", headsavename=" + headsavename + ", age="
				+ age + ", maid=" + maid + "]";
	}

	public String getMaid() {
		return maid;
	}

	public void setMaid(String maid) {
		this.maid = maid;
	}

}
