package com.milky.trackerWeb.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class LoginResponse extends MainResponse{
	@JsonProperty("success")
	private boolean success;
	@JsonProperty("userName")
    private String userName;
	@JsonProperty("message")
    private String message;
	@JsonProperty("email")
	private String email;
    
	@Override
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
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
	@Override
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
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
