package com.jiuquanlife.module.love.entity;

import java.util.Arrays;

public class BasicInfo {
	private String habits;
	private String occupation;
	private String income;
	private String weight;
	private String marriagestatus;
	private String bearstatus;
	private String character;
	private String edu;
	private String familystatus;
	private String housestatus;
	private String carstatus;
	private String smokestatus;
	private String drinkstatus;
	private String mateselection;
	private String resume;
	private String loveglow;
	private String[] hobby;
	private String telphone;
	private String qq;
	private String webchart;
	private String ispublic;

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWebchart() {
		return webchart;
	}

	public void setWebchart(String webchart) {
		this.webchart = webchart;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public String getMateselection() {
		return mateselection;
	}

	public void setMateselection(String mateselection) {
		this.mateselection = mateselection;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getLoveglow() {
		return loveglow;
	}

	public void setLoveglow(String loveglow) {
		this.loveglow = loveglow;
	}

	public String[] getHobby() {
		return hobby;
	}

	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}

	private UpdateData updateData;

	public UpdateData getUpdateData() {
		return updateData;
	}

	public void setUpdateData(UpdateData updateData) {
		this.updateData = updateData;
	}

	public String getHabits() {
		return habits;
	}

	public void setHabits(String habits) {
		this.habits = habits;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getMarriagestatus() {
		return marriagestatus;
	}

	public void setMarriagestatus(String marriagestatus) {
		this.marriagestatus = marriagestatus;
	}

	public String getBearstatus() {
		return bearstatus;
	}

	public void setBearstatus(String bearstatus) {
		this.bearstatus = bearstatus;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getFamilystatus() {
		return familystatus;
	}

	public void setFamilystatus(String familystatus) {
		this.familystatus = familystatus;
	}

	public String getHousestatus() {
		return housestatus;
	}

	public void setHousestatus(String housestatus) {
		this.housestatus = housestatus;
	}

	public String getCarstatus() {
		return carstatus;
	}

	public void setCarstatus(String carstatus) {
		this.carstatus = carstatus;
	}

	public String getSmokestatus() {
		return smokestatus;
	}

	public void setSmokestatus(String smokestatus) {
		this.smokestatus = smokestatus;
	}

	public String getDrinkstatus() {
		return drinkstatus;
	}

	public void setDrinkstatus(String drinkstatus) {
		this.drinkstatus = drinkstatus;
	}

	@Override
	public String toString() {
		return "BasicInfo [habits=" + habits + ", occupation=" + occupation
				+ ", income=" + income + ", weight=" + weight
				+ ", marriagestatus=" + marriagestatus + ", bearstatus="
				+ bearstatus + ", character=" + character + ", edu=" + edu
				+ ", familystatus=" + familystatus + ", housestatus="
				+ housestatus + ", carstatus=" + carstatus + ", smokestatus="
				+ smokestatus + ", drinkstatus=" + drinkstatus
				+ ", mateselection=" + mateselection + ", resume=" + resume
				+ ", loveglow=" + loveglow + ", hobby="
				+ Arrays.toString(hobby) + ", updateData=" + updateData
				+ ", getMateselection()=" + getMateselection()
				+ ", getResume()=" + getResume() + ", getLoveglow()="
				+ getLoveglow() + ", getHobby()=" + Arrays.toString(getHobby())
				+ ", getUpdateData()=" + getUpdateData() + ", getHabits()="
				+ getHabits() + ", getOccupation()=" + getOccupation()
				+ ", getIncome()=" + getIncome() + ", getWeight()="
				+ getWeight() + ", getMarriagestatus()=" + getMarriagestatus()
				+ ", getBearstatus()=" + getBearstatus() + ", getCharacter()="
				+ getCharacter() + ", getFamilystatus()=" + getFamilystatus()
				+ ", getHousestatus()=" + getHousestatus()
				+ ", getCarstatus()=" + getCarstatus() + ", getSmokestatus()="
				+ getSmokestatus() + ", getDrinkstatus()=" + getDrinkstatus()
				+ ", getEdu()=" + getEdu() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

}
