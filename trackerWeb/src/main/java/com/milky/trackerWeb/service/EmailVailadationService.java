package com.milky.trackerWeb.service;

import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class EmailVailadationService {
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private final String EMAIL_USERNAME;
    private final String EMAIL_PASSWORD;
    
    public EmailVailadationService(@Value("${email.username}") String eMAIL_USERNAME, @Value("${email.password}") String eMAIL_PASSWORD) {
		super();
		EMAIL_USERNAME = eMAIL_USERNAME;
		EMAIL_PASSWORD = eMAIL_PASSWORD;
	}
    

	public boolean isEmailValid(String email) {
		System.out.println("In regex class"+email);
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
		
	}

	
    protected boolean sendVerificationEmail(String recipientEmail, String verificationCode) {
    	String emailSubject = "Email Verification";
        String emailContent = "Your tracker.com email verification code is: " + verificationCode+" (Valid for 5 mins)";

        System.out.println("In sendVerificationEmail");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(emailSubject);
            message.setText(emailContent);
            System.out.println("In sendVerificationEmail Mid");
            Transport.send(message);
            System.out.println("Verification email sent successfully.");
            return true;
        } catch (MessagingException e) {
            System.out.println("Error sending verification email: " + e.getMessage());
            return false;
        }catch (Exception e) {
        	System.out.println("Error sending verification email: " + e.getMessage());
        	return false;
        }
    }

	
	public boolean verifyCode(int codeUser, int codeDB ) {
		
		return codeUser== codeDB;
	}
	
	public boolean passwordVerification(String password) {
		if (password!=null && password.length()>=7) {
			return true;
		}
		
		return false;
		
	}

}