package com.milky.trackerWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Retailer implements User{
	
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
	@Field("retailerID")
	@JsonProperty("retailerID")
	private int retailerID;
	@Field("accountStatus")
	@JsonProperty("accountStatus")
	private UserStatus status;

}
