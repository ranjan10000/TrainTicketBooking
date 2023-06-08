package com.app.ticket.serviceimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ticket.db.DbConnection;
import com.app.ticket.model.UserDetails;
import com.app.ticket.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	Connection connection = null;
	PreparedStatement ps;
	int response;
	private final String TABLE_NAME = "UserDetails";
	@Autowired
	DbConnection dbConnection;

	@Override
	public String registerUser(UserDetails userDetails) {

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
			} else {
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
	public String deleteUser(String deleteUser) throws SQLException {
		String deleteResponse = "UserNotFound";
		connection = DbConnection.getConnection();
		ResultSet rs = null;
		try {
			CallableStatement callableStatement = connection.prepareCall("{ call DeleteUser(?) }");
			callableStatement.setString(1, deleteUser);
			response = callableStatement.executeUpdate();
			if (response > 0) {
				deleteResponse = "User deleted successfully";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection, rs);
		}
		return deleteResponse;
	}

	@Override
	public String updateUser(UserDetails userDetails) {
		String updateResponse = "Update Failed";
		connection = DbConnection.getConnection();
		ResultSet rs = null;
		String query = "UPDATE " + TABLE_NAME + " SET fname=?,lname=?,pwd=? WHERE pno=?";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, userDetails.getFname());
			ps.setString(2, userDetails.getLname());
			ps.setString(3, userDetails.getPwd());
			ps.setString(4, userDetails.getPno());
			response = ps.executeUpdate();
			if (response > 0) {
				updateResponse = "Update Success";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbConnection.closeConnection(connection, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return updateResponse;
	}

	@Override
	public List<UserDetails> getAllUsers() {
		List<UserDetails> userList = new ArrayList<>();
		String query = "SELECT * FROM " + TABLE_NAME;
		connection = dbConnection.getConnection();
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserDetails userDetails = new UserDetails();
				userDetails.setFname(rs.getString("fname"));
				userDetails.setLname(rs.getString("lname"));
				userDetails.setPno(rs.getString("pwd"));
				userDetails.setPwd(rs.getString("pno"));
				userList.add(userDetails);
				ps.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbConnection.closeConnection(connection, rs);
			} catch (SQLException e) {

				e.printStackTrace();
			}
			return userList;
		}
	}
}
