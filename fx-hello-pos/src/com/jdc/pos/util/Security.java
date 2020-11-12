package com.jdc.pos.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.jdc.pos.entity.Employee;

public final class Security {
	
	private static Employee member;

	public static Employee getMember() {
		return member;
	}

	public static void setMember(Employee member) {
		Security.member = member;
	}
	
	public static String encodePassword(String pwd) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return Base64.getEncoder().encodeToString(digest.digest(pwd.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}