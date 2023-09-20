package com.milky.trackerWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.Product;
import com.milky.trackerWeb.service.ProductRetailerService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductRetailerController {
	@Autowired
	ProductRetailerService productRetailerService;
	
	
	@GetMapping(value = "/retailer", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getAllProduct(@CookieValue(value = "jwt_token", required = true) String jwtToken){
		
            return productRetailerService.findAllByEmail(jwtToken);
        
	}

}
