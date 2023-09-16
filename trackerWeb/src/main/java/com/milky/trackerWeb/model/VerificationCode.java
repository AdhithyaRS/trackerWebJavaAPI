package com.milky.trackerWeb.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.milky.trackerWeb.service.PhoneNumberValidationService;

@Document(collection = "emailVerification")
@TypeAlias("")
public class VerificationCode {
	
	@Id
	private String _id;
	@Field("email")
	@Indexed(unique = true)
	@JsonProperty("email")
	private String email;
	@Field("emailVerificationCode")
	@JsonProperty("emailVerificationCode")
	private String emailVerificationCode;
	@Field("phoneNumber")
	@Indexed(unique = true)
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	@Field("phoneNumberVerificationCode")
	@JsonProperty("phoneNumberVerificationCode")
	private String phoneNumberVerificationCode;
	@Field("verificationCode")
	@JsonProperty("verificationCode")
	private String verificationCode;
	@Indexed(expireAfterSeconds =  600) // TTL index with 600 seconds (10 minutes)
	@Field("expirationDate")
    private Date expirationDate;
	
	
	public VerificationCode() {
        super();
    }
	public void setResetCode(String email, String phoneNumber, String verificationCode) {
		this.phoneNumber = phoneNumber;
		this.email=email;
		this.verificationCode=verificationCode;
        this.expirationDate = calculateExpirationDate();
    }
	public VerificationCode(String phoneNumber, String emailVerificationCode , String phoneNumberVerificationCode) {
        this.phoneNumber = phoneNumber;
        this.emailVerificationCode = emailVerificationCode;
        this.phoneNumberVerificationCode = phoneNumberVerificationCode;
        this.expirationDate = calculateExpirationDate();
    }
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailVerificationCode() {
		return emailVerificationCode;
	}
	public void setEmailVerificationCode(String emailVerificationCode) {
		this.emailVerificationCode = emailVerificationCode;
	}
	public String getPhoneNumberVerificationCode() {
		return phoneNumberVerificationCode;
	}
	public void setPhoneNumberVerificationCode(String phoneNumberVerificationCode) {
		this.phoneNumberVerificationCode = phoneNumberVerificationCode;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return calendar.getTime();
    }
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

}
