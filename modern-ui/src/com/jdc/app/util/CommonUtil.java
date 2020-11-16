package com.jdc.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommonUtil {
	
	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy, MMM dd (E)");

	public static String format(LocalDate date) {
		return DF.format(date);
	}
	
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
