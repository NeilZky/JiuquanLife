package com.jiuquanlife.utils;

public class UrlUtils {
	
	public static final String PHOTO_URL_TEMPLATE = "http://5ijq.cn/hongliu/uc_server/avatar.php?uid=uidnumber&size=smal";
	
	public static String getPhotoUrl(String uid) {
		
		return PHOTO_URL_TEMPLATE.replace("uidnumber", uid);
	}
	
}
