package com.app.ticket.service;

import java.sql.SQLException;
import java.util.List;

import com.app.ticket.model.UserDetails;

public interface UserService {

	public String registerUser(UserDetails userDetails);

	public UserDetails loginUser(String pno, String pwd);

	public String deleteUser(String userDetails) throws SQLException;
	
	public String updateUser(UserDetails userDetails);
	
	public List<UserDetails> getAllUsers();
}