package com.milky.trackerWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "login")
@Component
public class Login {

	@Field("userType")
	@JsonProperty("userType")
    private UserType userType;
	@Field("userName")
	@JsonProperty("userName")
	private String userName;
	@Field("password")
	@JsonProperty("password")
	private String password;
	@Id
	@JsonProperty("email")
	private String email;
	@JsonProperty("reset")
	private boolean reset;
	@Field("userID")
	@JsonProperty("userID")
	private int userID;
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public enum UserType {
	    RETAILER,
	    CUSTOMER
	}
	
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType role) {
		this.userType = role;
	}
	public Login() {
		super();
	}
	@Override
	public String toString() {
		return "Login [userName=" + userName + ", password=" + password + ", email=" + email + ", reset=" + reset + "]";
	}

	
	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
