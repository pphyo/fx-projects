package com.jdc.app.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CommonUtil {
	
	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy (E)");
	private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("hh:mm a");

	public static String format(LocalDate date) {
		return DF.format(date);
	}
	
	public static String format(LocalTime time) {
		return TF.format(time);
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
