package com.milky.trackerWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.Product;
import com.milky.trackerWeb.service.JwtUtils;
import com.milky.trackerWeb.service.ProductRetailerService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductRetailerController {
	@Autowired
	ProductRetailerService productRetailerService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(value = "/retailer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getAllProduct(@CookieValue(value = "jwt_token", required = true) String jwtToken){
		try {
			System.out.println("Token : "+jwtToken);
			System.out.println("In fetch Products");
            if (!jwtUtils.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            List<Product> products = productRetailerService.findAllByEmail(jwtToken);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException ex) {
        	System.out.println("In dashboard catch block");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	}

}
