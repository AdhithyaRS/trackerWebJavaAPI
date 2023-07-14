package com.milky.trackerWeb.response;

import org.springframework.stereotype.Component;

@Component
public class LoginResponse {

	private boolean success;
    private String userName;
    private String message;
    
	public LoginResponse() {
		super();
	}
	public LoginResponse(boolean success, String userName, String message) {
		super();
		this.success = success;
		this.userName = userName;
		this.message = message;
		System.out.println(toString());
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getUsername() {
		return userName;
	}
	public void setUsername(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "LoginResponse [success=" + success + ", userName=" + userName + ", message=" + message + "]";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
