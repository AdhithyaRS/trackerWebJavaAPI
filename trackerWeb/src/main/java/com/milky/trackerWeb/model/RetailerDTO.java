package com.milky.trackerWeb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.milky.trackerWeb.model.User.UserStatus;

//@SuppressWarnings(value = { "unused" })
public class RetailerDTO {
    
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("email")
    private String email;
	@JsonProperty("status")
    private UserStatus status;
	@JsonProperty("phoneNumber")
    private String phoneNumber;
	@JsonProperty("companyBrand")
    private String companyBrand;
	@JsonProperty("companyCity")
    private String companyCity;
	@JsonProperty("companyAddress")
    private String companyAddress;
	@JsonProperty("companyPincode")
    private String companyPincode;
	@JsonProperty("companyState")
    private String companyState;

    public RetailerDTO(Retailer retailer) {
    	
    	userName = retailer.getUserName();
    	email= retailer.getEmail();
    	status = retailer.getStatus();
    	phoneNumber = retailer.getPhoneNumber();
    	companyBrand = retailer.getCompanyBrand();
    	companyCity = retailer.getCompanyCity();
    	companyAddress = retailer.getCompanyAddress();
    	companyPincode = retailer.getCompanyPincode();
    	companyState = retailer.getCompanyState();
	}
}