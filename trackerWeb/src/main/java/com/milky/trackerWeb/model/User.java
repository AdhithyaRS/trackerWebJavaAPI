package com.milky.trackerWeb.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "userTypeCustom")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Customer.class, name = "CUSTOMER"),
    @JsonSubTypes.Type(value = Retailer.class, name = "RETAILER")
})
public interface User {
	
	public enum UserStatus {
	    LOCKED,
	    UNLOCKED,
	    ACTIVE,
	    BLACKLIST
	}
	public enum UserType {
	    RETAILER,
	    CUSTOMER
	}
	UserType getUserType();
	void setUserType(UserType userType);
	String getUserName();
	void setUserName(String userName);
	String getPassword();
	void setPassword(String password);
	String getEmail();
	void setEmail(String email);
	boolean isReset();
	void setReset(boolean reset);
	UserStatus getStatus();
	void setStatus(UserStatus status);
	String toString();
	String getPhoneNumber();
	void setPhoneNumber(String phoneNumber);
	

}
