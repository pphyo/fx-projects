package com.solt.jdc.util;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

public class CommonUtil {
	
	private static Properties props;
	
	static {
		try {
			props = new Properties();
			props.load(new FileInputStream("app.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isEmpty(String str) {
		return null == str || str.isEmpty();
	}
	
	public static String getInfo(String key) {
		return props.getProperty(key);
	}
	
	public static String encodePassword(String rawPassword) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] arr = digest.digest(rawPassword.getBytes());
		
		return Base64.getEncoder().encodeToString(arr);
	}
	
}
