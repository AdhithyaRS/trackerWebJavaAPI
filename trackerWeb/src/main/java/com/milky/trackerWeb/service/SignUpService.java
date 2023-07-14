package com.milky.trackerWeb.service;


import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.milky.trackerWeb.model.Login;
import com.milky.trackerWeb.model.SignUp;
import com.milky.trackerWeb.repository.LoginDB;
import com.milky.trackerWeb.repository.SignUpDB;
import com.milky.trackerWeb.response.SignUpResponse;

@Component
public class SignUpService {
	@Autowired
	private EmailVailadationService emailVailadationService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SignUp signUp;
	@Autowired
	private SignUpDB signUpDB;
	@Autowired
	private Login login;
	@Autowired
	private LoginDB loginDB;
	@Autowired
    @Qualifier("primaryMongoTemplate")
    MongoTemplate primaryMongoTemplate;
	@Autowired
	private SignUpResponse signUpResponse;

    @Autowired
    MongoConverter converter;
	@Autowired
	private Random random;


	public SignUpResponse sendCode(SignUp signUp) {
		String email= signUp.getEmail();
		if(!emailVailadationService.isEmailValid(email)) {
			return new SignUpResponse(false,"Please enter a valid e-mail address");
		}
		if(loginDB.existsById(email)) {
			return new SignUpResponse(false,"User already exist, redirecting to login page");
		}
		if(signUpDB.existsById(email)) {
			signUpDB.deleteById(email);
		}
		int code= random.nextInt(900000) + 100000;
		signUpResponse.setSuccess(emailVailadationService.sendVerificationEmail(email ,code));
		if(signUpResponse.isSuccess()) {
			signUpDB.save(new SignUp(email,code));
			signUpResponse.setMessage("verification Code sent Successfully");
		}
		signUpResponse.setMessage("Error sending verification code, retry again");
		return signUpResponse;
	}
	public SignUpResponse registration(SignUp signUpCheck) {
		
		if(!emailVailadationService.passwordVerification(signUpCheck.getPassword())) {
			return new SignUpResponse(false, "Password length should not be less than 6 charecters");
		}
		
		signUp = signUpDB.findById(signUpCheck.getEmail()).orElseGet(() -> {
			
		    return null;
		});
		if(signUp==null) return new SignUpResponse(false, "Verification code is expired, resend the code");
		signUpResponse.setSuccess(emailVailadationService.verifyCode(signUpCheck.getVerificationCode(),signUp.getVerificationCode()));
		if(!signUpResponse.isSuccess()) {
			return new SignUpResponse(false, "Entered Code is incorrect.");
		}else if(!signUpCheck.getEmail().equals(signUp.getEmail())){
			return new SignUpResponse(false, "Entered email is incorrect.");
		}
		
		String hashedPassword = passwordEncoder.encode(signUpCheck.getPassword());
		
		login.setEmail(signUpCheck.getEmail());
		login.setUserName(signUpCheck.getUserName());
		login.setPassword(hashedPassword);
		
		loginDB.save(login);
		
		return new SignUpResponse(true, "Registration successfull, Please Login to continue");
	}

}
