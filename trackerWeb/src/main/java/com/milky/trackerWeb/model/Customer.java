package com.milky.trackerWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Document(collection = "customer")
@JsonTypeName("CUSTOMER")
public class Customer implements User{
	
	@Id
	private String _id;
	@Field("userType")
    private UserType userType = UserType.CUSTOMER;
	@Field("userName")
	//@JsonProperty("userName")
	private String userName;
	@Field("password")
	//@JsonProperty("password")
	private String password;
	@Field("email")
	@Indexed(unique = true)
	//@JsonProperty("email")
	private String email;
	//@JsonProperty("reset")
	private boolean reset;
	@Field("accountStatus")
	//@JsonProperty("accountStatus")
	private UserStatus status;
	@Field("phoneNumber")
	@Indexed(unique = true)
	//@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@Override
	public UserType getUserType() {
		return userType;
	}
	@Override
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	@JsonProperty("userName")
	@Override
	public String getUserName() {
		return userName;
	}
	@JsonProperty("userName")
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@JsonProperty("password")
	@Override
	public String getPassword() {
		return password;
	}
	@JsonProperty("password")
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonProperty("phoneNumber")
	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}
	@JsonProperty("phoneNumber")
	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@JsonProperty("email")
	@Override
	public String getEmail() {
		return email;
	}
	@JsonProperty("email")
	@Override
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonProperty("reset")
	@Override
	public boolean isReset() {
		return reset;
	}
	@JsonProperty("reset")
	@Override
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	@JsonProperty("accountStatus")
	@Override
	public UserStatus getStatus() {
		return status;
	}
	@JsonProperty("accountStatus")
	@Override
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Customer [_id=" + _id + ", userType=" + userType + ", userName=" + userName + ", password=" + password
				+ ", email=" + email + ", reset=" + reset + ", status=" + status + ", phoneNumber=" + phoneNumber + "]";
	}
	public Customer() {
		
	}
	

}
