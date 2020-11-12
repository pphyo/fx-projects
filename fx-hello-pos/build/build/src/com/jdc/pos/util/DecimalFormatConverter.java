package com.jdc.pos.util;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.util.StringConverter;

public class DecimalFormatConverter extends StringConverter<Integer> {

	private static final DecimalFormat DF = new DecimalFormat("#,##0");

	public static String format(int data) {
		return DF.format(data);
	}

	@Override
	public String toString(Integer obj) {
		if(null != obj)
			return DF.format(obj);
		return null;
	}

	@Override
	public Integer fromString(String str) {
		try {
			if(StringUtil.check(str))
				return DF.parse(str).intValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}