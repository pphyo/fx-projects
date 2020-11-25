package com.jdc.app.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CommonUtil {
	
	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy (E)");
	private static final DateTimeFormatter CART_DF = DateTimeFormatter.ofPattern("E, MMM d yyyy");
	private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("hh:mm a");
	
	private static final DecimalFormat NO_MMK_DF = new DecimalFormat("#,##0");
	private static final DecimalFormat MMK_DF = new DecimalFormat("#,##0 MMK");
	private static final DecimalFormat LAKH_DF = new DecimalFormat("#,##0 K");

	public static String noFormatMMK(int data) {
		return NO_MMK_DF.format(data);
	}
	
	public static String formatMMK(int data) {
		return MMK_DF.format(data);
	}
	
	public static String formatLakh(int data) {
		if(data > 9999)
			return LAKH_DF.format(data / 1000);
		return MMK_DF.format(data);
	}
	
	public static String formatCartDate(LocalDate date) {
		return CART_DF.format(date);
	}
	
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
