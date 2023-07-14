package com.milky.trackerWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.Login;
import com.milky.trackerWeb.model.SignUp;
import com.milky.trackerWeb.response.LoginResponse;
import com.milky.trackerWeb.response.SignUpResponse;
import com.milky.trackerWeb.service.LoginService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
	
	
	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	@CrossOrigin
    public LoginResponse check(@RequestBody Login login)
    {
		System.out.println(login.toString());
        return loginService.findByEmail(login);
    }
	@PostMapping("/passwordreset")
	@CrossOrigin
    public LoginResponse reset(@RequestBody Login login)
    {
		System.out.println(login.toString());
        return loginService.passWordReset(login);
    }
	@PostMapping("/verifycode")
	@CrossOrigin
    public SignUpResponse verifycode(@RequestBody SignUp signUp)
    {
		System.out.println(signUp.toString());
        return loginService.verifycode(signUp);
    }
}
