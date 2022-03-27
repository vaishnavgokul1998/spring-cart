package com.example.springsecurity.requestVO;

import java.util.Date;

public class UserDetailVO {
	
	private String userName;
	
	private String email;
	
	private String mobileNo;
	
	private String dob;
	
	private String jwtToken;
	
	private String role;
	
	private String status;
	
	private String id;
 
	private String designation;
	
	private Date memberJoinDate;


	public UserDetailVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDetailVO(Long id,String userName, String email, String mobileNo, String dob, String jwtToken, String role,
			String status,String designation,Date joinDate) {
		super();
		this.userName = userName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.jwtToken = jwtToken;
		this.role = role;
		this.status = status;
		this.id = id.toString();
		this.designation = designation;
		this.memberJoinDate = joinDate;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public Date getMemberJoinDate() {
		return memberJoinDate;
	}

	public void setMemberJoinDate(Date memberJoinDate) {
		this.memberJoinDate = memberJoinDate;
	}
	

}
