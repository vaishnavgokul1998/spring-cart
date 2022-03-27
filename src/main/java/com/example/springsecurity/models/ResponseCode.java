package com.example.springsecurity.models;

import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseCode {

	private int code;
	
	private String status;
	
	private String message;
	
	
	@JsonIgnore
	private Authentication auth;
	
	
	
	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
