package com.jiuquanlife.module.love.entity;

public class FriendInfo {
	private String uid;
	private String fuid;
	private String fusername;
	private String dateline;
	private String cuid;
	private FromInfo fromInfo;
	private String maid;

	private String sortLetters; // 显示数据拼音的首字母

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFuid() {
		return fuid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
	}

	public String getFusername() {
		return fusername;
	}

	public void setFusername(String fusername) {
		this.fusername = fusername;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public FromInfo getFromInfo() {
		return fromInfo;
	}

	public void setFromInfo(FromInfo fromInfo) {
		this.fromInfo = fromInfo;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getMaid() {
		return maid;
	}

	public void setMaid(String maid) {
		this.maid = maid;
	}

	@Override
	public String toString() {
		return "FriendInfo [uid=" + uid + ", fuid=" + fuid + ", fusername="
				+ fusername + ", dateline=" + dateline + ", cuid=" + cuid
				+ ", fromInfo=" + fromInfo + ", maid=" + maid
				+ ", sortLetters=" + sortLetters + "]";
	}

}
