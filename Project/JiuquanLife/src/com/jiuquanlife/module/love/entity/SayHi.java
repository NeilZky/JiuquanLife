package com.jiuquanlife.module.love.entity;

public class SayHi {
	private String uid;
	private String fromuid;
	private String fromusername;
	private String note;
	private String dateline;
	private String status;

	private FromInfo fromInfo;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFromuid() {
		return fromuid;
	}

	public void setFromuid(String fromuid) {
		this.fromuid = fromuid;
	}

	public String getFromusername() {
		return fromusername;
	}

	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public FromInfo getFromInfo() {
		return fromInfo;
	}

	public void setFromInfo(FromInfo fromInfo) {
		this.fromInfo = fromInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SayHi [uid=" + uid + ", fromuid=" + fromuid + ", fromusername="
				+ fromusername + ", note=" + note + ", dateline=" + dateline
				+ ", status=" + status + ", fromInfo=" + fromInfo + "]";
	}

	

}
