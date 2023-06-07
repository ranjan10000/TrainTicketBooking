package com.app.ticket.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ticket.db.DbConnection;
import com.app.ticket.model.UserDetails;
import com.app.ticket.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final String TABLE_NAME = "UserDetails";
	@Autowired
	DbConnection dbConnection;

	@Override
	public String registerUser(UserDetails userDetails) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?,?)";

		try {

			connection = DbConnection.getConnection();
			ps = connection.prepareStatement(query);
			ps.setString(1, userDetails.getFname());
			ps.setString(2, userDetails.getLname());
			ps.setString(3, userDetails.getPwd());
			ps.setString(4, userDetails.getPno());
			rs = ps.executeQuery();
			ps.close();

		}

		catch (SQLException e) {
			// System.out.println("SQLException" + e);
		} finally {
			try {
				dbConnection.closeConnection(connection, rs);
			} catch (Exception e) {
				System.out.println("database connection not closed " + e);
			}
		}
		return "Query Executed Successfully !!!! ";
	}

	@Override
	public UserDetails loginUser(String phoneno, String password) {
		UserDetails userDetails = null;
		Connection connection = null;
		PreparedStatement ps;
		ResultSet rs = null;
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE pno=? AND pwd = ?";
		connection = dbConnection.getConnection();
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, phoneno);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				userDetails = new UserDetails();
				userDetails.setFname(rs.getString("fname"));
				userDetails.setLname(rs.getString("lname"));
				userDetails.setPno(rs.getString("pno"));
				userDetails.setPwd(rs.getString("pwd"));
				System.out.println(userDetails.toString());
			}else {
				System.out.println("Error");
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				dbConnection.closeConnection(connection, rs);
			} catch (Exception e) {
				System.out.println("database connection not closed " + e);
			}
		}
		return userDetails;
	}

	@Override
	public String deleteUser(UserDetails userDetails) {
		
		return null;
	}
}
