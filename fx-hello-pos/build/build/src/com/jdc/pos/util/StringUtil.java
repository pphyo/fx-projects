package com.jdc.pos.util;

public class StringUtil {
	
	public static boolean check(String str) {
		return null != str && !str.isEmpty();
	}

	public static String concatZero(String str) {
		if(str.length() == 1) {
			return "0".concat(str);
		}
		return str;
	}
	
}
