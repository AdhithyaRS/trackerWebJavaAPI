package com.milky.trackerWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "login")
@Component
public class Login {


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
