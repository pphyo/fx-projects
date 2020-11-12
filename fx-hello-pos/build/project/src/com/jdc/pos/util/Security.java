package com.jdc.pos.util;

import com.jdc.pos.entity.Employee;

public final class Security {
	
	private static Employee member;

	public static Employee getMember() {
		return member;
	}

	public static void setMember(Employee member) {
		Security.member = member;
	}
	
}