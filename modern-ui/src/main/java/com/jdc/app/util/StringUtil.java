package com.jdc.app.util;

public class StringUtil {
	
	public static boolean isEmpty(String str) {
		return null == str || str.isEmpty();
	}
	
	public static boolean isNumeric(String str) {
		boolean numeric = true;
		
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			numeric = false;
		}
		
		return numeric;
	}

}
