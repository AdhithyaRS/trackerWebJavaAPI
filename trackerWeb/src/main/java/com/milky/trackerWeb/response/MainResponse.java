package com.milky.trackerWeb.response;



import com.milky.trackerWeb.model.Login.UserType;

public interface MainResponse {

	boolean isSuccess();

	String getEmail();
	UserType getUserType();
	int getUserID();
	
	
}
