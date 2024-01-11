package com.milky.trackerWeb.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.milky.trackerWeb.model.User.UserType;


@Component
public class JwtUtils {
    private final Key SECRET_KEY;
    private static final long EXPIRATION_TIME = 3600000; // 1 hour
    
    public JwtUtils(@Value("${jwt.secretKey}") String secretKey) {
        this.SECRET_KEY = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");;
    }

    public String generateRegistrationToken(String phoneNumber, UserType type) throws Exception{
    	try {
	    	Date now = new Date(); // Capture the current time
	        Date expirationDate = new Date(System.currentTimeMillis() + (EXPIRATION_TIME/2));
	
	        return Jwts.builder()
	                .setSubject(phoneNumber)
	                .claim("roles", type.name())
	                .setIssuedAt(now)
	                .setId(UUID.randomUUID().toString())
	                .setExpiration(expirationDate)
	                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
	                .compact();
    	}catch (NullPointerException e) {
	        // Handle null pointer exceptions
	        e.printStackTrace();
	        System.out.println("Null value encountered.");
	        throw e;
	    } catch (IllegalArgumentException e) {
	        // Handle illegal argument exceptions
	        e.printStackTrace();
	        System.out.println("Invalid argument provided.");
	        throw e;
	    } catch (SignatureException e) {
	        // Handle signature exceptions
	        e.printStackTrace();
	        System.out.println("Error in signing the token.");
	        throw e;	    
	    } catch (Exception e) {
	        // Handle other exceptions
	        e.printStackTrace();
	        System.out.println("An unknown error occurred.");
	        throw e;
	    }
    }

    public void validateToken(String token) throws Exception {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            if (claimsJws.getBody().getExpiration().before(new Date())) {
                throw new ExpiredJwtException(null, null, "Token has expired");
            }
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (MalformedJwtException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error validating token");
            throw e;
        }
    }
    

    public String getPhoneNumberFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
    }
    public String getUserTypeFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().get("roles", String.class);
    }

	public String generateSigninToken(String phoneNumber, UserType type) throws Exception{
		try {
			Date now = new Date(); // Capture the current time
	        Date expirationDate = new Date(System.currentTimeMillis() + (type==UserType.CUSTOMER? EXPIRATION_TIME/10:EXPIRATION_TIME/10));

	        return Jwts.builder()
	                .setSubject(phoneNumber)
	                .claim("roles", type.name())
	                .setIssuedAt(now)
	                .setId(UUID.randomUUID().toString())
	                .setExpiration(expirationDate)
	                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
	                .compact();
		}catch (NullPointerException e) {
	        // Handle null pointer exceptions
	        e.printStackTrace();
	        System.out.println("Null value encountered.");
	        throw e;
	    } catch (IllegalArgumentException e) {
	        // Handle illegal argument exceptions
	        e.printStackTrace();
	        System.out.println("Invalid argument provided.");
	        throw e;
	    } catch (SignatureException e) {
	        // Handle signature exceptions
	        e.printStackTrace();
	        System.out.println("Error in signing the token.");
	        throw e;	    
	    } catch (Exception e) {
	        // Handle other exceptions
	        e.printStackTrace();
	        System.out.println("An unknown error occurred.");
	        throw e;
	    }
	}
}
