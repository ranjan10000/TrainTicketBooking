package com.app.ticket.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;

@Repository
public class DbConnection {
	public static void main(String[] args) {
		DbConnection.getConnection();

	}

	private static Connection con;

	public static Connection getConnection() {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		try {
			Class.forName(rb.getString("driverName"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
//			System.out.println(rb.getString("connectionString"));
//			System.out.println(rb.getString("username"));
//			System.out.println(rb.getString("password"));
			rb.getString("connectionString");
			rb.getString("username");
			rb.getString("password");
			
			con = DriverManager.getConnection(rb.getString("connectionString"), rb.getString("username"),
					rb.getString("password"));
			System.out.println("Connection Success!!");
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return con;

	}

	public boolean closeConnection(Connection connect, ResultSet resultSet) throws SQLException {
		if (connect != null) {
			connect.close();
			System.out.println("Connection Closed");
			if (resultSet != null) {
				resultSet.close();
				System.out.println("ResultSet Closed");
			}
			return true;
		}
		return false;

	}
}
