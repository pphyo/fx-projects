package com.jdc.pos.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss a");
	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:ss a");
	
	public static String format(LocalDateTime dt) {
		return dt.format(DTF);
	}
	
	public static String format(LocalDate d) {
		return d.format(DF);
	}
	
	public static String format(LocalTime t) {
		return t.format(TF);
	}
	
}