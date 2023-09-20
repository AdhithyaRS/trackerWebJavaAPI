package com.milky.trackerWeb.service;



import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.milky.trackerWeb.model.Customer;
import com.milky.trackerWeb.model.RegisterJwt;
import com.milky.trackerWeb.model.Retailer;
import com.milky.trackerWeb.model.User;
import com.milky.trackerWeb.model.User.UserStatus;
import com.milky.trackerWeb.model.User.UserType;
import com.milky.trackerWeb.model.VerificationCode;
import com.milky.trackerWeb.repository.CustomerDb;
import com.milky.trackerWeb.repository.RegisterJwtDb;
import com.milky.trackerWeb.repository.RetailerDb;
import com.milky.trackerWeb.repository.VerificationCodeDb;
import com.milky.trackerWeb.response.MainResponse;
import com.milky.trackerWeb.response.SignUpResponse;
import com.mongodb.DuplicateKeyException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class SignUpService {
	
	@Autowired
	private EmailVailadationService emailValidationService;
	@Autowired
	private CustomerDb customerDb;
	@Autowired
	private RetailerDb retailerDb;
	@Autowired
	private RegisterJwtDb registerJwtDb;
	@Autowired
	private VerificationCodeDb verificationCodeDb;
	@Autowired
	private PhoneNumberValidationService phoneNumberValidationService;
	@Autowired
	private Random random;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtils jwtUtils;
	
	public MainResponse verifyUser(User user, HttpServletResponse response) {
		//RegisterJwt registerJwt = new RegisterJwt();
		String email = user.getEmail(); 
		String phoneNumber = user.getPhoneNumber();
		if(user.isReset()) {
			if(registerJwtDb.existsByPhoneNumber(phoneNumber)) {
				registerJwtDb.deleteByPhoneNumber(phoneNumber);
			}else {
				registerJwtDb.deleteByEmail(email);
			}
		}
		if(!emailValidationService.isEmailValid(email)) {
			return new SignUpResponse(false,"Please enter a valid e-mail address");
		}
		if(customerDb.existsByEmail(email)) {
			return new SignUpResponse(false,"User already exist with this e-mail as Customer, Please Sign in!!");
		}else if(retailerDb.existsByEmail(email)) {
			return new SignUpResponse(false,"User already exist with this e-mail as Retailer, Please Sign in!!");
		}
		if(!phoneNumberValidationService.isPhoneNumberValid(phoneNumber)) {
			return new SignUpResponse(false,"Please enter a valid Phone Number");
			
		}else if(retailerDb.existsByPhoneNumber(phoneNumber)) {
			return new SignUpResponse(false,"User already exist with this Phone Number as Retailer, Please Sign in!!");
		}else if(customerDb.existsByPhoneNumber(phoneNumber)) {
			return new SignUpResponse(false,"User already exist with this Phone Number as Customer, Please Sign in!!");
		}
		if(registerJwtDb.existsByPhoneNumber(phoneNumber) || registerJwtDb.existsByEmail(email)) {
			//registerJwt = registerJwtDb.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
			return new SignUpResponse(true,"Reset");
		}
		String token;
		try {
			token =jwtUtils.generateRegistrationToken(user.getPhoneNumber() , user.getUserType());
		}catch (Exception e) {
		    return new SignUpResponse(false, "An unknown error occurred.");
		}
		
		try {
			registerJwtDb.save(new RegisterJwt(phoneNumber,email, token));
		}catch (DuplicateKeyException e) {
			return new SignUpResponse(false,"SignUp process already on-going");
		}catch (Exception e) {
			return new SignUpResponse(false,"SignUp process already on-going");
		}
		Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/signUp");
        response.addCookie(jwtCookie);
		return new SignUpResponse(true,"e-mail and phone number verified in DB!!");
		
	}

	public SignUpResponse sendCode(User user, String jwtToken) {
		SignUpResponse signUpResponse = new SignUpResponse();
		String email = user.getEmail();
		String phoneNumber = user.getPhoneNumber();
		RegisterJwt registerJwt = new RegisterJwt();
		registerJwt = registerJwtDb.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
		if(registerJwt==null || !registerJwt.getJwtToken().equals(jwtToken)) return new SignUpResponse(false, "Unauthorized");
		if(verificationCodeDb.existsByPhoneNumber(phoneNumber)) {
			System.out.println("In deleting process the existing codes: "+phoneNumber);
			verificationCodeDb.deleteByPhoneNumber(phoneNumber);
		}else {
			System.out.println("In deleting process the existing codes with email: "+email);
			verificationCodeDb.deleteByEmail(email);
		}
		String emailCode= ""+(random.nextInt(900000) + 100000);
		signUpResponse.setSuccess(emailValidationService.sendVerificationEmail(email ,emailCode));
		if(!signUpResponse.isSuccess()) {
			signUpResponse.setMessage("Error sending verification code to email-id, retry again later");
			return signUpResponse;
		}else {
			String phoneNumberCode= ""+(random.nextInt(900000) + 100000);
			signUpResponse.setSuccess(phoneNumberValidationService.sendVerificationPhoneNumber(phoneNumber ,phoneNumberCode));
			if(!signUpResponse.isSuccess()) {
				signUpResponse.setMessage("Error sending verification code to Phone Number, retry again later");
			}else {
				try {
	                verificationCodeDb.save(new VerificationCode(phoneNumber,email, emailCode, phoneNumberCode));
	                signUpResponse.setMessage("Verification Codes sent successfully to Mobile Number And email-id");
	            } catch (Exception e) {
	                signUpResponse.setSuccess(false);
	                signUpResponse.setMessage("Error saving verification codes to the database");
	                e.printStackTrace();
	            }
			}
			return signUpResponse;
		}
	}


	public MainResponse verifyCode(VerificationCode verificationCodeCheck, String jwtToken) {
	    try {
	    	VerificationCode verificationCode = new VerificationCode();
	    	SignUpResponse signUpResponse = new SignUpResponse();
	    	RegisterJwt registerJwt = new RegisterJwt();
			registerJwt = registerJwtDb.findByPhoneNumber(verificationCodeCheck.getPhoneNumber()).orElse(null);
			if(registerJwt==null || !registerJwt.getJwtToken().equals(jwtToken)) return new SignUpResponse(false, "Unauthorized/ Sign up process is already on-going");
	        verificationCode = verificationCodeDb.findByPhoneNumber(verificationCodeCheck.getPhoneNumber()).orElse(null);
	        if (verificationCode == null) {
	            return new SignUpResponse(false, "Verification code is expired, resend the code");
	        }
	        signUpResponse.setSuccess((verificationCodeCheck.getEmailVerificationCode().equals(verificationCode.getEmailVerificationCode())));
	        if (!signUpResponse.isSuccess()) {
	            return new SignUpResponse(false, "Entered Email verification Code is incorrect.");
	        }
	        signUpResponse.setSuccess((verificationCodeCheck.getPhoneNumberVerificationCode().equals(verificationCode.getPhoneNumberVerificationCode())));

	        if (!signUpResponse.isSuccess()) {
	            return new SignUpResponse(false, "Entered Phone Number verification Code is incorrect.");
	        }
	        verificationCodeDb.deleteByPhoneNumber(verificationCode.getPhoneNumber());
	        return new SignUpResponse(true, "Entered verification Codes are correct.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new SignUpResponse(false, "An error occurred while verifying the codes.");
	    }
	}


	public MainResponse registration(User user, HttpServletResponse response, String jwtToken) {
		try {
			String phoneNumber = user.getPhoneNumber();
			RegisterJwt registerJwt = new RegisterJwt();
			registerJwt = registerJwtDb.findByPhoneNumber(phoneNumber).orElse(null);
			if(registerJwt==null || !registerJwt.getJwtToken().equals(jwtToken)) return new SignUpResponse(false, "Unauthorized");
	        String hashedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(hashedPassword);
	        if(user.getUserType()== UserType.RETAILER) {
	        	user.setStatus(UserStatus.LOCKED);
	        	retailerDb.save((Retailer)user);
			}else {
				user.setStatus(UserStatus.ACTIVE);
				customerDb.save((Customer)user);
			}
	        Cookie jwtCookie = new Cookie("jwt_token", null);
	        jwtCookie.setMaxAge(0);
	        jwtCookie.setHttpOnly(true);
	        jwtCookie.setPath("/signUp");  // set the path to what you set it to when creating the cookie
	        response.addCookie(jwtCookie);
	        registerJwtDb.deleteByPhoneNumber(phoneNumber);
	        return new SignUpResponse(true, "Registration successfull!!");
	    } catch (DuplicateKeyException dke) {
	        return new SignUpResponse(false, "Email or phone number already in use.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new SignUpResponse(false, "An error occurred during registration.");
	    }
	}
}
