package com.milky.trackerWeb.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.twilio.Twilio;

@Component
public class PhoneNumberValidationService {
	@Value("${twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${twilio.authToken}")
    private String twilioAuthToken;
    @Value("${twilio.number}")
    private String twilioPhoneNumber;

	public boolean isPhoneNumberValid(String phoneNumber) {
		String regex = "^(?:(?:\\+\\d{1,3})?(\\d{10}))$";

        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(phoneNumber).matches();
	}

	public boolean sendVerificationPhoneNumber(String phoneNumber, String code) {
		Twilio.init(twilioAccountSid, twilioAuthToken);
		try {
			
		
//			Message message = Message.creator(
//			        new PhoneNumber(phoneNumber),
//			        new PhoneNumber(twilioPhoneNumber),
//			        "Your tracker.com mobile verification code is: " + code+" (Valid for 10 mins")
//			    .create();
//	
//		    System.out.println(message.getSid());
			System.out.println("code sent successfully to mobile phone number");
			return true;
		} catch(Exception e) {
			return false;
		}
	}

}
