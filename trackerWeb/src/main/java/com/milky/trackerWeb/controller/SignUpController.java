package com.milky.trackerWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.SignUp;
import com.milky.trackerWeb.response.SignUpResponse;
import com.milky.trackerWeb.service.SignUpService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SignUpController {
	@Autowired
	SignUpService signUpService;
	
	

	
	@PostMapping("/sendVerificationCode")
	@CrossOrigin
    public SignUpResponse sendCode(@RequestBody SignUp signUp)
    {
		System.out.println(signUp.toString());
        return signUpService.sendCode(signUp);
    }
	@PostMapping("/register")
	@CrossOrigin
    public SignUpResponse registration(@RequestBody SignUp signUp)
    {
		System.out.println(signUp.toString());
        return signUpService.registration(signUp);
    }
	
	
}