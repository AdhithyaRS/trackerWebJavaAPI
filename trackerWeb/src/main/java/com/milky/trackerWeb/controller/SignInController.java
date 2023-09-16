package com.milky.trackerWeb.controller;


import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milky.trackerWeb.model.User;
import com.milky.trackerWeb.model.VerificationCode;
import com.milky.trackerWeb.response.MainResponse;
import com.milky.trackerWeb.service.SignInService;




@RestController
@RequestMapping("/signIn")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SignInController {

	@Autowired
	private SignInService signInService;
	
	@PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> verifyUser(@RequestBody User user, HttpServletResponse response)
    {
           
        return ResponseEntity.ok(signInService.verifyUser(user,response));
        
    }
	
	@PostMapping(value = "/passwordreset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> passwordReset(@RequestBody User user, HttpServletResponse response)
    {
		System.out.println(user.toString());
		return ResponseEntity.ok(signInService.passwordReset(user, response));
    }
	
	@PostMapping(value = "/sendCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> sendCode(@CookieValue(value = "jwt_reset_token", required = true) String jwtToken, 
            @RequestBody User user)
    {
		System.out.println(user.toString());
		return ResponseEntity.ok(signInService.sendCode(jwtToken,user));
    }
	
	@PostMapping(value = "/verifycode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> verifyCode(@CookieValue(value = "jwt_reset_token", required = true) String jwtToken,@RequestBody VerificationCode verificationCode)
    {
		System.out.println(verificationCode.toString());
		return ResponseEntity.ok(signInService.verifyCode(verificationCode, jwtToken));
    }
	
	@PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MainResponse> changePassword(@CookieValue(value = "jwt_reset_token", required = true) String jwtToken,@RequestBody User user,HttpServletResponse response)
    {
		System.out.println(user.toString());
		return ResponseEntity.ok(signInService.changePassword(user, jwtToken, response));
    }
}
