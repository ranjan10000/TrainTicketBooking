package com.app.ticket.model;

import jakarta.persistence.Entity;

@Entity
public class UserDetails {



	private String fname;
	private String lname;
	private String pwd;
	private String pno;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	@Override
	public String toString() {
		return "UserDetails [fname=" + fname + ", lname=" + lname + ", pwd=" + pwd + ", pno=" + pno + "]";
	}
	
}
