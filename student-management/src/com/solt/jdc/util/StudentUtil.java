package com.solt.jdc.util;

public class StudentUtil {
	
	public static String convertStuId(int id) {
		
		String str = "STU-";
		
		if(id < 10) {
			return String.format("%s%s%d", str, "0000", id);
		} if(id < 100) {
			return String.format("%s%s%d", str, "000", id);
		} if(id < 1000) {
			return String.format("%s%s%d", str, "00", id);
		} if(id < 10000) {
			return String.format("%s%s%d", str, "0", id);
		} else {
			return String.format("%s%d", str, id);
		}
	}

}
