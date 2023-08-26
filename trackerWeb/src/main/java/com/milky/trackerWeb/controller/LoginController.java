package com.milky.trackerWeb.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.Login;
import com.milky.trackerWeb.model.Login.UserType;
import com.milky.trackerWeb.model.SignUp;
import com.milky.trackerWeb.response.MainResponse;
import com.milky.trackerWeb.service.JwtUtils;
import com.milky.trackerWeb.service.LoginService;




@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
    private JwtUtils jwtUtils;
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> check(@RequestBody Login login, HttpServletResponse response)
    {
		System.out.println(login.toString());
		MainResponse mainResponse = loginService.findByEmail(login);
		
		if (mainResponse.isSuccess()) {
			String token =jwtUtils.generateToken(mainResponse.getUserID() , mainResponse.getUserType());

			
	        Cookie jwtCookie = new Cookie("jwt_token", token);
	        jwtCookie.setHttpOnly(true);

	        if(mainResponse.getUserType()==UserType.CUSTOMER) {
	        	jwtCookie.setPath("/customer");
	        }else jwtCookie.setPath("/retailer");
	        
	        response.addCookie(jwtCookie);
			
		}
           
        return ResponseEntity.ok(mainResponse);
        
    }
	@PostMapping(value = "/passwordreset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> reset(@RequestBody Login login)
    {
		System.out.println(login.toString());
		return ResponseEntity.ok(loginService.passWordReset(login));
    }
	@PostMapping(value = "/verifycode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> verifycode(@RequestBody SignUp signUp)
    {
		System.out.println(signUp.toString());
		return ResponseEntity.ok(loginService.verifycode(signUp));
    }
}
