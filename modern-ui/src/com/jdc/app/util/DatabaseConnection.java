package com.jdc.app.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.jdc.app.PosException;

public class DatabaseConnection {
	
	private static Properties prop;
	private static String url;
	private static String usr;
	private static String pwd;
	
	private DatabaseConnection() {throw new PosException("Can't invoke!");};
	
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
