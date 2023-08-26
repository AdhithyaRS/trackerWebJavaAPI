package com.milky.trackerWeb.service;


import java.util.NoSuchElementException;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import com.milky.trackerWeb.model.Login;
import com.milky.trackerWeb.model.SignUp;
import com.milky.trackerWeb.repository.LoginDB;
import com.milky.trackerWeb.repository.SignUpDB;
import com.milky.trackerWeb.response.LoginResponse;
import com.milky.trackerWeb.response.SignUpResponse;

@Component
public class LoginService {
	@Autowired
	private Login login;
	@Autowired
	private SignUpResponse signUpResponse;
	@Autowired
	private SignUp signUp;
	@Autowired
	private SignUpDB signUpDB;
	@Autowired
	private Random random;
	@Autowired
	private LoginResponse loginResponse; 
	@Autowired
	private EmailVailadationService emailVailadationService;
	@Autowired
	private LoginDB loginDB;
	@Autowired
	private PasswordEncoder passwordEncoder;
//    @Autowired
//    @Qualifier("primaryMongoTemplate")
//    MongoTemplate primaryMongoTemplate;
    @Autowired
    private Login loginFromDB;

//    @Autowired
//    MongoConverter converter;

    public LoginResponse findByEmail(Login login) {
    	System.out.println("compiled"+login.toString());
    	String username;
    	String email = login.getEmail();
    	try {
    		loginFromDB= loginDB.findById(login.getEmail()).get();
    		username= loginFromDB.getUserName();
            email= loginFromDB.getEmail();
    	}
    	catch(NoSuchElementException e){
    		return new LoginResponse(false, email,"User Not Found. Please sign up/check e-mail id.");
    	}
    	if(login.getUserType()!=loginFromDB.getUserType()) {
    		return new LoginResponse(false, email,"Please login as "+loginFromDB.getUserType()+"!!");
    	}
        
    	String rawPassword = login.getPassword();
        String hashedPasswordFromDB = loginFromDB.getPassword();
        
        if (passwordEncoder.matches(rawPassword, hashedPasswordFromDB)) {
        	loginResponse.setEmail(email);
        	loginResponse.setSuccess(true);
        	loginResponse.setUserName(username);
        	loginResponse.setMessage("password Verified.");
        	loginResponse.setUserType(loginFromDB.getUserType());
        	loginResponse.setUserID(loginFromDB.getUserID());
            return loginResponse;
            
        } else {
            return new LoginResponse(false, username,"Incorrect password.");
        }
        
}

	public LoginResponse passWordReset(Login login) {
		// TODO Auto-generated method stub
    	String email = login.getEmail();
    	System.out.println("In passWordReset"+ email);
    	try {
    		loginFromDB= loginDB.findById(login.getEmail()).get();
            email= loginFromDB.getEmail();
    	}
    	catch(NoSuchElementException e){
    		return new LoginResponse(false, email,"User Not Found. Please SIGN UP/check e-mail id.");
    	}
    	
    	if(signUpDB.existsById(email)) {
			signUpDB.deleteById(email);
		}
    	System.out.println("In passWordReset mid "+ email);
    	int code= random.nextInt(900000) + 100000;
		loginResponse.setSuccess(emailVailadationService.sendVerificationEmail(email, code));
		System.out.println("In passWordReset middle"+ email);
		if(loginResponse.isSuccess()) {
			signUpDB.save(new SignUp(email,code));
			loginResponse.setMessage("verification Code sent Successfully");
			return loginResponse;
		}
		System.out.println("In passWordReset end"+ email);
		loginResponse.setMessage("Error sending verification code, retry again");
		return loginResponse;
		
	}

	public SignUpResponse verifycode(SignUp signUpCheck) {
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
		
		 login= loginDB.findById(signUp.getEmail()).get();
		 login.setPassword(hashedPassword);
		 loginDB.save(login);
		return new SignUpResponse(true, "Password reset successfull, Please SIGN IN to continue");
	}
}
