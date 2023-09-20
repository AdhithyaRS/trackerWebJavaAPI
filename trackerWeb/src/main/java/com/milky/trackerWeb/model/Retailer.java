package com.milky.trackerWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Document(collection = "retailer")
@JsonTypeName("RETAILER")
public class Retailer implements User{
	
	@Id
	private String _id;
	@Field("userType")
    private UserType userType = UserType.RETAILER;
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
	@Field("companyBrand")
	//@JsonProperty("companyBrand")
	private String companyBrand;
	@Field("companyCity")
	//@JsonProperty("companyCity")
	private String companyCity;
	@Field("companyAddress")
	//@JsonProperty("companyAddress")
	private String companyAddress;
	@Field("companyPincode")
	//@JsonProperty("companyPincode")
	private String companyPincode;
	@Field("companyState")
	//@JsonProperty("companyState")
	private String companyState;
	
	
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
	@JsonProperty("companyBrand")
	public String getCompanyBrand() {
		return companyBrand;
	}
	@JsonProperty("companyBrand")
	public void setCompanyBrand(String companyBrand) {
		this.companyBrand = companyBrand;
	}
	@JsonProperty("companyCity")
	public String getCompanyCity() {
		return companyCity;
	}
	@JsonProperty("companyCity")
	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}
	@JsonProperty("companyAddress")
	public String getCompanyAddress() {
		return companyAddress;
	}
	@JsonProperty("companyAddress")
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	@JsonProperty("companyPincode")
	public String getCompanyPincode() {
		return companyPincode;
	}
	@JsonProperty("companyPincode")
	public void setCompanyPincode(String companyPincode) {
		this.companyPincode = companyPincode;
	}
	@JsonProperty("companyState")
	public String getCompanyState() {
		return companyState;
	}
	@JsonProperty("companyState")
	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}
	
	
	@Override
	public String toString() {
		return "Retailer [_id=" + _id + ", userType=" + userType + ", userName=" + userName + ", password=" + password
				+ ", email=" + email + ", reset=" + reset + ", status=" + status + ", phoneNumber=" + phoneNumber
				+ ", companyBrand=" + companyBrand + ", companyCity=" + companyCity + ", companyAddress="
				+ companyAddress + ", companyPincode=" + companyPincode + ", companyState=" + companyState + "]";
	}
	public Retailer() {
		
	}

}
