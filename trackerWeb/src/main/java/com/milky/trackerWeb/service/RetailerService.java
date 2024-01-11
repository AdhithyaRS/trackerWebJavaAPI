package com.milky.trackerWeb.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milky.trackerWeb.model.Retailer;
import com.milky.trackerWeb.model.RetailerDTO;
import com.milky.trackerWeb.repository.RetailerDb;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RetailerService {
	@Autowired
	private RetailerDb retailerDb;
	@Autowired
	private JwtUtils jwtUtils;
	

    
	public RetailerDTO getRetailer(String jwtToken) {
		String phoneNumber = jwtUtils.getPhoneNumberFromToken(jwtToken);
		
		Retailer retailer =  retailerDb.findByPhoneNumber(phoneNumber).orElse(null);
		RetailerDTO retailerDTO = new RetailerDTO(retailer);
		return retailerDTO;
		
	}



	public void signOutRetailer(HttpServletResponse response) {
		// TODO Auto-generated method stub
		Cookie jwtCookie = new Cookie("jwt_signIn_token_retailer", null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/retailer");  // set the path to what you set it to when creating the cookie
        response.addCookie(jwtCookie);
		
	}
}
