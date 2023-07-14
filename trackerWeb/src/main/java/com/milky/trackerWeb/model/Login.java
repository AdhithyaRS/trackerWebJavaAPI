package com.milky.trackerWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;


@Document(collection = "login")
@Component
public class Login {
	

	@Override
	public String toString() {
		return "Login [userName=" + userName + ", password=" + password + ", email=" + email + "]";
	}

	
	@Field("userName")
	private String userName;
	@Field("password")
	private String password;
	@Id
	private String email;

	public Login() {
		super();
	}
	public Login(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public Login(String userName, String password, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.email=email;
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
