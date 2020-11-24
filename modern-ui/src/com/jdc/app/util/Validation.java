package com.jdc.app.util;

import java.util.List;

import com.jdc.app.PosException;

public class Validation {
	
	public static void validate(String str, String message) {
		if(StringUtil.isEmpty(str))
			throw new PosException(message);
	}
	
	public static<T> void validate(List<?> str, String message) {
		if(str.isEmpty())
			throw new PosException(message);
	}

}
