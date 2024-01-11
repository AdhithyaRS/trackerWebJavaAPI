package com.milky.trackerWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.CustomerDTO;
import com.milky.trackerWeb.service.CustomerService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> retailerCheck() {
	    System.out.println("In customer Controller");

	    return ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "/profile",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerDTO> cuatomerGet(@CookieValue(value = "jwt_signIn_token_customer", required = true) String jwtToken){
		System.out.println("In customer Profile Controller");
		
		return ResponseEntity.ok(customerService.getCustomer(jwtToken));
        
	}
	
	@GetMapping(value = "/signout",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> retailerSignOut(HttpServletResponse response){
		System.out.println("In customer SignOut Controller");
		customerService.signOutCustomer(response);
		return ResponseEntity.ok().build();
        
	}

}
