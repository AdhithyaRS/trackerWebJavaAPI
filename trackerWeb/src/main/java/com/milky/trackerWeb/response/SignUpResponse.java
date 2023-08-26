package com.milky.trackerWeb.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.milky.trackerWeb.model.Login.UserType;

@Component
public class SignUpResponse implements MainResponse{

	@JsonProperty("message")
	private String message;
	@JsonProperty("success")
	private boolean success;
	@JsonProperty("userType")
	private UserType userType;
	
	public SignUpResponse() {
		super();
	}

	public SignUpResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserType getUserType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getUserID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
