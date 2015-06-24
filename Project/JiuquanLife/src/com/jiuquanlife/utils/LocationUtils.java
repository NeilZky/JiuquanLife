package com.jiuquanlife.utils;


public class LocationUtils {

	private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0; 
	
	public static double convertToBdLon(double latitude, double longitude)  
	{  
	    double x = longitude, y = latitude;  
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);  
	    return z * Math.cos(theta) + 0.0065;  
	}  
	
	public static double convertToBdLat(double latitude, double longitude)  
	{  
	    double x = longitude, y = latitude;  
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);  
	    return z * Math.sin(theta) + 0.006;  
	}
	  
	/**
	 * @param latitude
	 * @param longitude
	 * @param gg_lat
	 * @param gg_lon
	 */
	public static double bdConvertToHxLon(double latitude, double longitude)  
	{  
	    double x = longitude - 0.0065, y = latitude - 0.006;  
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
	    return z * Math.cos(theta);  
	}  	
	
	public static double bdConvertToHxLat(double bd_lat, double bd_lon)  
	{  
	    double x = bd_lon - 0.0065, y = bd_lat - 0.006;  
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
	    return z * Math.sin(theta);  
	}  	
}
