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
public class SignUp {

	@Id
	@JsonProperty("email")
	private String email;
	@Field("verificationCode")
	@JsonProperty("verificationCode")
	private int verificationCode;
	@Indexed(expireAfterSeconds =  300) // TTL index with 300 seconds (5 minutes)
    @Field("expirationDate")
    private Date expirationDate;
	@JsonProperty("password")
	private String password;
	@JsonProperty("userName")
	private String userName;

	@Override
	public String toString() {
		return "SignUp [email=" + email + ", verificationCode=" + verificationCode + ", expirationDate="
				+ expirationDate + ", password=" + password + ", userName=" + userName + "]";
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public SignUp() {
		super();
	}
	
	public SignUp(String email, int verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.expirationDate = calculateExpirationDate();
    }
	private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        calendar.add(Calendar.MINUTE, 5); // Set expiration time to 5 minutes from now
        return calendar.getTime();
    }
	public SignUp(String email, int verificationCode, String password, String userName) {
		super();
		this.email = email;
		this.verificationCode = verificationCode;
		this.password = password;
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(int verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
