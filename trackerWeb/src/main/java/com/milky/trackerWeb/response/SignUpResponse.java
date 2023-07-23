package com.milky.trackerWeb.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class SignUpResponse extends MainResponse{

	@JsonProperty("message")
	private String message;
	@JsonProperty("success")
	private boolean success;
	
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

}
