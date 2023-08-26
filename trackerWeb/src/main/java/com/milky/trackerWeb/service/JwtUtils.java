package com.milky.trackerWeb.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.milky.trackerWeb.model.Login.UserType;


@Component
public class JwtUtils {
    private final Key SECRET_KEY;
    private static final long EXPIRATION_TIME = 360000; // 1 hour
    
    public JwtUtils(@Value("${jwt.secretKey}") String secretKey) {
        this.SECRET_KEY = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");;
    }

    public String generateToken(int userID, UserType type) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(Integer.toString(userID))
                .claim("roles", type.name())
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
        	System.out.println(e);
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
    }
}
