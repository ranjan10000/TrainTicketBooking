package com.app.ticket.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ticket.model.UserDetails;
import com.app.ticket.service.UserService;

@RestController
public class BookingController {

	@Autowired
	UserService userService;

	@GetMapping("/test")
	public String check() {
		return "Success";
	}

	@PostMapping("/userBooking")
	public String userBooking(@RequestBody UserDetails userDetails) {

		return userService.registerUser(userDetails);
	}

	@PostMapping("/login")
	public UserDetails UserLogin(@RequestParam String pno, @RequestParam String pwd) {
		System.out.println(pno);
		System.out.println(pwd);
		return userService.loginUser(pno, pwd);
	}

	@DeleteMapping("/deleteUser")
	public String DeleteUser(@RequestParam("pno") String deleteUser) throws SQLException {
		return userService.deleteUser(deleteUser);
	}
	@PutMapping("/updateUser")
	public String updateUser(@RequestBody UserDetails userDetails) {
		return userService.updateUser(userDetails);
	}
	@GetMapping("/getAllUser")
	public List<UserDetails> getAllUser(){
		
		return userService.getAllUsers();
	}
}
