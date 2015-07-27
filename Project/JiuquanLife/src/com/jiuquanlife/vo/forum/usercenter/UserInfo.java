package com.jiuquanlife.vo.forum.usercenter;

import java.io.Serializable;

public class UserInfo implements Serializable{

	private static final long serialVersionUID = -305723980012507153L;
	public static final String TABLE_NAME = "UserInfo";
	public static final String COLUMN_UID = "uid";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_SIGNATURE = "signature";

	public int uid;
	public int gender;
	public String icon;
	public String distance;
	public String name;
	public String signature;
	public int isFirend;
	public int isFollow;
	public String lastLogin;
	public String dateline;
	public int credits;
}
