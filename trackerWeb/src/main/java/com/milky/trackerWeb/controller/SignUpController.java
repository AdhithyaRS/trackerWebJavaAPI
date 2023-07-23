package com.milky.trackerWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.SignUp;
import com.milky.trackerWeb.response.MainResponse;
import com.milky.trackerWeb.service.SignUpService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SignUpController {
	@Autowired
	SignUpService signUpService;
	
	

	
	@PostMapping(value = "/sendVerificationCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public MainResponse sendCode(@RequestBody SignUp signUp)
    {
		System.out.println(signUp.toString());
        return signUpService.sendCode(signUp);
    }
	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public MainResponse registration(@RequestBody SignUp signUp)
    {
		System.out.println(signUp.toString());
        return signUpService.registration(signUp);
    }
	
	
}