package com.milky.trackerWeb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDTO {
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("email")
    private String email;
	@JsonProperty("phoneNumber")
    private String phoneNumber;

    public CustomerDTO(Customer customer) {
    	
    	userName = customer.getUserName();
    	email= customer.getEmail();
    	phoneNumber = customer.getPhoneNumber();
	}

}
