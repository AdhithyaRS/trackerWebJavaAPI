package com.milky.trackerWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milky.trackerWeb.model.Customer;
import com.milky.trackerWeb.model.CustomerDTO;
import com.milky.trackerWeb.repository.CustomerDb;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomerService {
	@Autowired
	private CustomerDb customerDb;
	@Autowired
	private JwtUtils jwtUtils;
	

    
	public CustomerDTO getCustomer(String jwtToken) {
		String phoneNumber = jwtUtils.getPhoneNumberFromToken(jwtToken);
		
		Customer customer =  customerDb.findByPhoneNumber(phoneNumber).orElse(null);
		CustomerDTO customerDTO = new CustomerDTO(customer);
		return customerDTO;
		
	}



	public void signOutCustomer(HttpServletResponse response) {
		// TODO Auto-generated method stub
		Cookie jwtCookie = new Cookie("jwt_signIn_token_customer", null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");  // set the path to what you set it to when creating the cookie
        response.addCookie(jwtCookie);
		
	}
}
