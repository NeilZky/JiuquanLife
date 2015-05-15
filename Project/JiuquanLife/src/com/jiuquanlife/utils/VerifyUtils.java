package com.jiuquanlife.utils;

import java.util.regex.Pattern;

public class VerifyUtils {
	
	public static boolean isValidTel(String tel) {
		
		String phone = "(/^0{0,1}(13[0-9]|145|15[7-9]|150|151|152|153|155|156|18[0-9])[0-9]{8}$/i)|(/^(\\d{3,4}\\-)?[1-9]\\d{6,7}$/)";
	    return Pattern.matches(phone, tel);
	}
	
}
