package com.milky.trackerWeb.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "registerJwt")
@TypeAlias("")
public class RegisterJwt {
	
	@Id
    private String _id;
	@Indexed(unique = true)
    @Field("phoneNumber")
    @JsonProperty("phoneNumber")
	private String phoneNumber;
	@Indexed(unique = true)
    @Field("email")
    @JsonProperty("email")
	private String email;
	@Field("JwtToken")
	@JsonProperty("JwtToken")
	private String JwtToken;
	@Indexed(expireAfterSeconds =  1800) // TTL index with 1800 seconds (30 minutes)
	@Field("expirationDate")
    private Date expirationDate;
	@Override
    public String toString() {
        return "Register [id=" + _id + ", phoneNumber=" + phoneNumber + ", JwtToken=" + JwtToken + "]";
    }
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getJwtToken() {
		return JwtToken;
	}
	public void setJwtToken(String jwtToken) {
		JwtToken = jwtToken;
	}
	public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RegisterJwt(String phoneNumber,String email, String JwtToken) {
        this.phoneNumber = phoneNumber;
        this.JwtToken = JwtToken;
        this.email = email;
        this.expirationDate = calculateExpirationDate();
    }
	public RegisterJwt() {
        super();
    }
	private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return calendar.getTime();
    }

}
