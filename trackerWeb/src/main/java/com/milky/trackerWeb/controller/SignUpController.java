package com.milky.trackerWeb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milky.trackerWeb.model.User;
import com.milky.trackerWeb.model.VerificationCode;
import com.milky.trackerWeb.response.MainResponse;
import com.milky.trackerWeb.service.SignUpService;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/signUp")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SignUpController {
	@Autowired
	SignUpService signUpService;
	
	
	@PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> verifyUser(@RequestBody User user, HttpServletResponse response)
    {
		try {
	        // Log or debug to see if the JSON is what you expect
	        System.out.println("Received JSON: " + new ObjectMapper().writeValueAsString(user));
	        System.out.println(user.toString());

	        return ResponseEntity.ok(signUpService.verifyUser(user, response));
	    } catch (JsonMappingException e) {
	        // Log error
	        System.out.println("Error mapping JSON to object: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    } catch (JsonProcessingException e) {
	        // Log error
	        System.out.println("Error processing JSON: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
    }
	
	@PostMapping(value = "/sendVerificationCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> sendCode(@CookieValue(value = "jwt_token", required = true) String jwtToken, 
            @RequestBody User user)
    {
		System.out.println(user.toString());
        return ResponseEntity.ok(signUpService.sendCode(user, jwtToken));
    }
	
	@PostMapping(value = "/verifyCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> verifyCode(@CookieValue(value = "jwt_token", required = true) String jwtToken,@RequestBody VerificationCode verificationCode)
    {
		System.out.println(verificationCode.toString());
        return ResponseEntity.ok(signUpService.verifyCode(verificationCode, jwtToken));
    }
	
	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> registration(@CookieValue(value = "jwt_token", required = true) String jwtToken,@RequestBody User user, HttpServletResponse response)
    {
		System.out.println(user.toString());
        return ResponseEntity.ok(signUpService.registration(user, response, jwtToken));
    }
	
	
}