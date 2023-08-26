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
import com.milky.trackerWeb.model.UserIdCounter;
import com.milky.trackerWeb.repository.LoginDB;
import com.milky.trackerWeb.repository.SignUpDB;
import com.milky.trackerWeb.repository.UniqueIDDB;
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
    private UniqueIDDB uniqueIDDB;
    @Autowired
    MongoConverter converter;
	@Autowired
	private Random random;


	public SignUpResponse sendCode(SignUp signUp) {
		String email= signUp.getEmail();
		//String mobileNumber = signUp.ge
		if(!emailVailadationService.isEmailValid(email)) {
			return new SignUpResponse(false,"Please enter a valid e-mail address");
		}
		if(loginDB.existsById(email)) {
			return new SignUpResponse(false,"User already exist, Please login");
		}
		if(signUpDB.existsById(email)) {
			signUpDB.deleteById(email);
		}
		int code= random.nextInt(900000) + 100000;
		signUpResponse.setSuccess(emailVailadationService.sendVerificationEmail(email ,code));
		if(signUpResponse.isSuccess()) {
			signUpDB.save(new SignUp(email,code));
			signUpResponse.setMessage("Verification Code sent successfully");
		}else signUpResponse.setMessage("Error sending verification code, retry again");
		return signUpResponse;
	}
	
	public int generateUniqueUserId() {
	    int maxRetries = 5;
	    int retryDelay = 100; // Initial delay in milliseconds

	    for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
	        try {
	        	UserIdCounter counter = uniqueIDDB.findById("user_id_counter").orElse(null);
                if (counter == null) {
                    // Handle the situation appropriately
                    return 1;
                }
                
                counter.setValue(counter.getValue() + 1);
                uniqueIDDB.save(counter);

                int newUserId = counter.getValue();
                return newUserId;
	        } catch (Exception e) {
	            // Handle the exception, log it, etc.
	            e.printStackTrace();
	            
	            // Implement exponential backoff
	            retryDelay *= 2; // Double the delay for each retry
	            try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                }
	        }
	    }

	    // Return a backup ID or handle the situation appropriately
	    return 1;
	}

	
	public SignUpResponse registration(SignUp signUpCheck) {
		
		if(!emailVailadationService.passwordVerification(signUpCheck.getPassword())) {
			return new SignUpResponse(false, "Password length should not be less than 7 charecters");
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
		int uniqueId= generateUniqueUserId();
		if(uniqueId==1) {
			return new SignUpResponse(false, "Concurrent User SignUp, Try after sometime!!");
		}
		login.setUserID(uniqueId);
		login.setEmail(signUpCheck.getEmail());
		login.setUserName(signUpCheck.getUserName());
		login.setPassword(hashedPassword);
		login.setUserType(signUpCheck.getUserType());
		loginDB.save(login);
		
		return new SignUpResponse(true, "Registration successfull, Please Login to continue");
	}

}
