package com.jiuquanlife.utils;

import java.text.DecimalFormat;

public class DecimalUtils {
	
	public static double keepSizeTwo(double input){
		DecimalFormat df = new DecimalFormat(".##");
		String resStr = df.format(input);
		return Double.parseDouble(resStr);
	}
}
