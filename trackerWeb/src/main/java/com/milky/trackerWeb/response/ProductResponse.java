package com.milky.trackerWeb.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.milky.trackerWeb.model.Login.UserType;

public class ProductResponse implements MainResponse{
	
	@JsonProperty("userType")
	private UserType userType;

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserType getUserType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getUserID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
