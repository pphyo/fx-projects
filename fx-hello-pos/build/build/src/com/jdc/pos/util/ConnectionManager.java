package com.jdc.pos.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	
	private static Properties prop;
	private static String url;
	private static String usr;
	private static String pwd;
	
	private ConnectionManager() {throw new PosException("Can't invoke!");};
	
	static {
		try {
			prop = new Properties();
			prop.load(new FileInputStream("application.properties"));
			url = prop.getProperty("pos.url");
			usr = prop.getProperty("pos.usr");
			pwd = prop.getProperty("pos.pwd");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getDbConnection() throws SQLException {
		return DriverManager.getConnection(url, usr, pwd);
	}

}
