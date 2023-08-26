package com.milky.trackerWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "customer")
@Component
public class Customer implements User{
	
	
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
	@Field("customerID")
	@JsonProperty("customerID")
	private int customerID;
	@Field("accountStatus")
	@JsonProperty("accountStatus")
	private UserStatus status;

}
