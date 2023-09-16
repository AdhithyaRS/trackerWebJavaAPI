package com.milky.trackerWeb.service;


import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.milky.trackerWeb.model.Retailer;
import com.milky.trackerWeb.model.Customer;
import com.milky.trackerWeb.model.RegisterJwt;
import com.milky.trackerWeb.model.User;
import com.milky.trackerWeb.model.VerificationCode;
import com.milky.trackerWeb.model.User.UserStatus;
import com.milky.trackerWeb.model.User.UserType;
import com.milky.trackerWeb.repository.CustomerDb;
import com.milky.trackerWeb.repository.RegisterJwtDb;
import com.milky.trackerWeb.repository.RetailerDb;
import com.milky.trackerWeb.repository.VerificationCodeDb;
import com.milky.trackerWeb.response.MainResponse;
import com.milky.trackerWeb.response.SignInResponse;
import com.milky.trackerWeb.response.SignUpResponse;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import com.mongodb.MongoExecutionTimeoutException;
import com.mongodb.MongoQueryException;
import com.mongodb.MongoSocketReadException;
import com.mongodb.MongoTimeoutException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SignInService {
	@Autowired
	private CustomerDb customerDb;
	@Autowired
	private RetailerDb retailerDb;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private EmailVailadationService emailValidationService;
	@Autowired
	private VerificationCodeDb verificationCodeDb;
	@Autowired
	private PhoneNumberValidationService phoneNumberValidationService;
	@Autowired
	private RegisterJwtDb registerJwtDb;

	public MainResponse verifyUser(User user, HttpServletResponse response) {
		String rawPassword = user.getPassword();
		String phoneNumber= user.getPhoneNumber();
		String email = user.getEmail();
		try {
		    if(user.getUserType() == UserType.CUSTOMER) {
		        Customer customer = email == null ? customerDb.findByPhoneNumber(phoneNumber).orElse(null) : customerDb.findByEmail(email).orElse(null);
		        if(customer == null) {
		            return new SignInResponse(false, "User not found as customer!!");
		        } else if(customer.getStatus() == UserStatus.LOCKED) {
		            return new SignInResponse(false, "Account is locked!! Please contact customer service");
		        }
		        String hashedPassword = customer.getPassword();
		        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
		        	user=customer;
		            return new SignInResponse(false, "Incorrect password!!");
		        }
		    } else {
		        Retailer retailer = email == null ? retailerDb.findByPhoneNumber(phoneNumber).orElse(null) : retailerDb.findByEmail(email).orElse(null);
		        if(retailer == null) {
		            return new SignInResponse(false, "User not found as retailer!!");
		        } else if(retailer.getStatus() == UserStatus.LOCKED) {
		            return new SignInResponse(false, "Account is locked!! Please contact customer service");
		        }
		        String hashedPassword = retailer.getPassword();
		        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
		        	user=retailer;
		            return new SignInResponse(false, "Incorrect password!!");
		        }
		    }
		    String token =jwtUtils.generateSigninToken(user.getPhoneNumber() , user.getUserType());
			Cookie jwtCookie = new Cookie("jwt_token", token);
	        jwtCookie.setHttpOnly(true);
	        jwtCookie.setPath("/");
	        response.addCookie(jwtCookie);
			return new SignInResponse(true, "Verification successfull");
		} catch (NoSuchElementException e) {
		    // Handle exception when an optional object is empty
		    e.printStackTrace();
		    return new SignInResponse(false, "Element not found.");
		} catch (MongoTimeoutException e) {
		    // MongoDB operation timed out
		    e.printStackTrace();
		    return new SignInResponse(false, "MongoDB operation timed out.");
		} catch (MongoQueryException e) {
		    // Incorrect query syntax
		    e.printStackTrace();
		    return new SignInResponse(false, "Incorrect MongoDB query.");
		} catch (MongoSocketReadException e) {
		    // Error in reading from MongoDB
		    e.printStackTrace();
		    return new SignInResponse(false, "Error reading from MongoDB.");
		} catch (MongoExecutionTimeoutException e) {
		    // MongoDB execution timed out on the server
		    e.printStackTrace();
		    return new SignInResponse(false, "MongoDB execution timed out.");
		} catch (MongoException e) {
		    // General MongoDB exception
		    e.printStackTrace();
		    return new SignInResponse(false, "MongoDB error.");
		} catch (Exception e) {
		    // General exception
		    e.printStackTrace();
		    return new SignInResponse(false, "An unknown error occurred.");
		}
	}

	public MainResponse passwordReset(User user, HttpServletResponse response) {
		SignInResponse signInResponse = (SignInResponse)verifyUser(user, null);
		if(!signInResponse.getMessage().equals("Incorrect password!!")) {
			return signInResponse;
		}
		if(user.isReset()) {
			if(registerJwtDb.existsByPhoneNumber(user.getPhoneNumber())) {
				registerJwtDb.deleteByPhoneNumber(user.getPhoneNumber());
			}
		}
		
		if(registerJwtDb.existsByPhoneNumber(user.getPhoneNumber())) {
			//registerJwt = registerJwtDb.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
			return new SignInResponse(true,"Reset");
		}
		String token;
		try {
			token =jwtUtils.generateRegistrationToken(user.getPhoneNumber() , user.getUserType());
		}catch (Exception e) {
		    return new SignInResponse(false, "An unknown error occurred.");
		}
		try {
			registerJwtDb.save(new RegisterJwt(user.getPhoneNumber(),user.getEmail(), token));
		}catch (DuplicateKeyException e) {
			return new SignInResponse(false,"Password reset process already on-going");
		}catch (Exception e) {
			return new SignInResponse(false,"Password reset process already on-going");
		}
		Cookie jwtCookie = new Cookie("jwt_reset_token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/signIn");
        response.addCookie(jwtCookie);
        return new SignInResponse(true,"Credentials Verified!!");
	}

	public MainResponse sendCode(String jwtToken, User user) {
		String email = user.getEmail();
		String phoneNumber= user.getPhoneNumber();
		RegisterJwt registerJwt = new RegisterJwt();
		if(email==null) {
			registerJwt =registerJwtDb.findByPhoneNumber(phoneNumber).orElse(null);
		}else {
			registerJwt =registerJwtDb.findByEmail(email).orElse(null);
		}
		if(registerJwt==null || !registerJwt.getJwtToken().equals(jwtToken)) {
			return new SignInResponse(false, "Unauthorized");
		}else {
			user.setEmail(registerJwt.getEmail());
			user.setPhoneNumber(registerJwt.getPhoneNumber());
		}
		SignInResponse signInResponse = new SignInResponse();
		
		if(verificationCodeDb.existsByPhoneNumber(user.getPhoneNumber())) {
			verificationCodeDb.deleteByPhoneNumber(user.getPhoneNumber());
		}
		String verificationCode;
		if(phoneNumber==null) {
			verificationCode= ""+123456;//""+(random.nextInt(900000) + 100000);
			signInResponse.setSuccess(emailValidationService.sendVerificationEmail(email ,verificationCode));
			if(!signInResponse.isSuccess()) {
				signInResponse.setMessage("Error sending verification code to email-id, retry again later");
				return signInResponse;
			}
		}else {
			verificationCode= ""+123456;//random.nextInt(900000) + 100000;
			signInResponse.setSuccess(phoneNumberValidationService.sendVerificationPhoneNumber(phoneNumber ,verificationCode));
			if(!signInResponse.isSuccess()) {
				signInResponse.setMessage("Error sending verification code to Phone Number, retry again later");
				return signInResponse;
			}
		}
		try {
			VerificationCode setVerificationCode= new VerificationCode();
			setVerificationCode.setResetCode(user.getEmail(),user.getPhoneNumber(), verificationCode);
            verificationCodeDb.save(setVerificationCode);
        } catch (Exception e) {
        	signInResponse.setSuccess(false);
        	signInResponse.setMessage("Error saving verification codes to the database");
            e.printStackTrace();
            return signInResponse;
        }
		if(email==null) {
        	return new SignInResponse(true, "Code sent successfully to "+phoneNumber+"!!");
        }else {
        	return new SignInResponse(true, "Code sent successfully to "+email+"!!");
        }
	}
	
	public MainResponse verifyCode(VerificationCode verificationCodeCheck, String jwtToken ) {
		try {
			String email = verificationCodeCheck.getEmail();
			String phoneNumber= verificationCodeCheck.getPhoneNumber();
			RegisterJwt registerJwt = new RegisterJwt();
			VerificationCode verificationCode = new VerificationCode();
			SignInResponse signInResponse= new SignInResponse();
			if(email==null) {
				registerJwt =registerJwtDb.findByPhoneNumber(phoneNumber).orElse(null);
				verificationCode= verificationCodeDb.findByPhoneNumber(phoneNumber).orElse(null);
			}else {
				registerJwt =registerJwtDb.findByEmail(email).orElse(null);
				verificationCode= verificationCodeDb.findByEmail(email).orElse(null);
			}
			if(registerJwt==null || !registerJwt.getJwtToken().equals(jwtToken)) {
				return new SignInResponse(false, "Unauthorized");
			}
			signInResponse.setSuccess((verificationCodeCheck.getVerificationCode().equals(verificationCode.getVerificationCode())));
	        if (!signInResponse.isSuccess()) {
	            return new SignInResponse(false, "Entered verification Code is incorrect.");
	        }
	        return new SignInResponse(true, "Entered verification Codes are correct.");
		}catch (Exception e) {
	        e.printStackTrace();
	        return new SignInResponse(false, "An error occurred while verifying the codes.");
		}
	}

	public MainResponse changePassword(User user, String jwtToken, HttpServletResponse response) {
		try {
			String rawPassword = user.getPassword();
			String email = user.getEmail();
			String phoneNumber= user.getPhoneNumber();
			RegisterJwt registerJwt = new RegisterJwt();
			VerificationCode verificationCode = new VerificationCode();
			if(email==null) {
				registerJwt =registerJwtDb.findByPhoneNumber(phoneNumber).orElse(null);
				verificationCode= verificationCodeDb.findByPhoneNumber(phoneNumber).orElse(null);
			}else {
				registerJwt =registerJwtDb.findByEmail(email).orElse(null);
				verificationCode= verificationCodeDb.findByEmail(email).orElse(null);
			}
			if(registerJwt==null || !registerJwt.getJwtToken().equals(jwtToken)) {
				return new SignInResponse(false, "Unauthorized");
			}
			String hashedPassword = passwordEncoder.encode(rawPassword);
			try {
			    if (user.getUserType() == UserType.CUSTOMER) {
			        Customer customer = customerDb.findByPhoneNumber(registerJwt.getPhoneNumber()).orElse(null);
			        if (customer != null) {
			            customer.setPassword(hashedPassword);
			            customerDb.save(customer);
			        } else {
			        	return new SignInResponse(false, "An error occurred while saving password in DB!!");
			        }
			    } else {
			        Retailer retailer = retailerDb.findByPhoneNumber(registerJwt.getPhoneNumber()).orElse(null);
			        if (retailer != null) {
			            retailer.setPassword(hashedPassword);
			            retailerDb.save(retailer);
			        } else {
			        	return new SignInResponse(false, "An error occurred while saving password in DB!!");
			        }
			    }
			} catch (Exception e) {
			    // Handle MongoDB-related exceptions here
			    e.printStackTrace(); // You can log the exception or take appropriate actions
			    return new SignInResponse(false, "An error occurred while saving password in DB!!");
			}

	        verificationCodeDb.deleteByPhoneNumber(verificationCode.getPhoneNumber());
	        registerJwtDb.deleteByPhoneNumber(registerJwt.getPhoneNumber());
	        Cookie jwtCookie = new Cookie("jwt_reset_token", null);
	        jwtCookie.setMaxAge(0);
	        jwtCookie.setHttpOnly(true);
	        jwtCookie.setPath("/signIn");  // set the path to what you set it to when creating the cookie
	        response.addCookie(jwtCookie);
	        return new SignInResponse(true, "Password reset successfull!! Please Sign in.");
		}catch (Exception e) {
	        e.printStackTrace();
	        return new SignInResponse(false, "An error occurred while re-setting password!!");
		}
	}
	
}
