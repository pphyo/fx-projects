package com.jdc.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.jdc.app.entity.Employee;

public class Security {

	private static Employee employee;

	public static Employee getEmployee() {
		return employee;
	}

	public static void setEmployee(Employee employee) {
		Security.employee = employee;
	}
	
	public static String encodePassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return Base64.getEncoder().encodeToString(md.digest(password.getBytes()));
	}
	
}
