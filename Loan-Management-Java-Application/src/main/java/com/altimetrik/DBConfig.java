package com.altimetrik;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConfig {

	private static final String URL = "jdbc:mysql://localhost:3306/loan_management";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";

	public static Connection getConnection() throws CustomException {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			throw new CustomException("Failed to connect to the database", e);
		}
	}
}