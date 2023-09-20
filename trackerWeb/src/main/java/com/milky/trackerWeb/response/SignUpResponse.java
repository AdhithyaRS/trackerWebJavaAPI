package com.milky.trackerWeb.response;



import com.fasterxml.jackson.annotation.JsonProperty;


public class SignUpResponse implements MainResponse{
	@JsonProperty("success")
	private boolean success;
	@JsonProperty("message")
    private String message;
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
	public SignUpResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	public SignUpResponse() {
		super();
	}

}
