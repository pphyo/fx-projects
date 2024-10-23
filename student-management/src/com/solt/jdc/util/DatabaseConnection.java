package com.solt.jdc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static final String URL = "jdbc:mysql://localhost:3306/student_db";
	private static final String USR = "root";
	private static final String PWD = "pyaephyo";
	
	public static Connection getSqlConnection() throws SQLException {
		return DriverManager.getConnection(URL, USR, PWD);
	}

}
