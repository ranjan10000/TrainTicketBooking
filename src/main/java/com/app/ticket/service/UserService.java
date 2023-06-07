package com.app.ticket.service;

import com.app.ticket.model.UserDetails;

public interface UserService {

	public String registerUser(UserDetails userDetails);
	
	public UserDetails loginUser(String pno,String pwd);
	
	public String deleteUser(UserDetails userDetails);
}