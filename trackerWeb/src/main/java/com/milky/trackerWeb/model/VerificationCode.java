package com.milky.trackerWeb.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "emailVerification")
@Component
public class VerificationCode {
	@Id
	@JsonProperty("email")
	private String email;
	@Field("emailVerificationCode")
	@JsonProperty("emailVerificationCode")
	private int emailVerificationCode;
	@Field("phoneNumberVerificationCode")
	@JsonProperty("phoneNumberVerificationCode")
	private int phoneNumberVerificationCode;
	@Indexed(expireAfterSeconds =  300) // TTL index with 300 seconds (5 minutes)
	@Field("expirationDate")
    private Date expirationDate;
	
	
	
	public VerificationCode(String email, int emailVerificationCode) {
        this.email = email;
        this.emailVerificationCode = emailVerificationCode;
        this.expirationDate = calculateExpirationDate();
    }
	public VerificationCode(String email, int emailVerificationCode , int phoneNumberVerificationCode) {
        this.email = email;
        this.emailVerificationCode = emailVerificationCode;
        this.phoneNumberVerificationCode = phoneNumberVerificationCode;
        this.expirationDate = calculateExpirationDate();
    }
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEmailVerificationCode() {
		return emailVerificationCode;
	}
	public void setEmailVerificationCode(int emailVerificationCode) {
		this.emailVerificationCode = emailVerificationCode;
	}
	public int getPhoneNumberVerificationCode() {
		return phoneNumberVerificationCode;
	}
	public void setPhoneNumberVerificationCode(int phoneNumberVerificationCode) {
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
        calendar.add(Calendar.MINUTE, 5); // Set expiration time to 5 minutes from now
        return calendar.getTime();
    }

}
