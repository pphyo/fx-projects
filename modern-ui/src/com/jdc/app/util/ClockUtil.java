package com.jdc.app.util;

public class ClockUtil {

	public static String concatZero(String value) {
		if(value.length() == 1)
			return "0".concat(value);
		return value;
	}
	
	public static int getTwelveHour(int value) {
		if(value > 12)
			return value % 12;
		return value;
	}
	
	public static String getAmPm(int value) {
		if(value > 12)
			return "PM";
		return "AM";
	}
	
}
