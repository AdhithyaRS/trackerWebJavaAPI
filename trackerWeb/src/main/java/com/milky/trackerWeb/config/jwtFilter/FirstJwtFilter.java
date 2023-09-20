package com.milky.trackerWeb.config.jwtFilter;

import java.io.IOException;

import com.milky.trackerWeb.service.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FirstJwtFilter implements Filter {
	private JwtUtils jwtUtils;

    public FirstJwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
	    res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
	    res.setHeader("Access-Control-Allow-Credentials", "true");
	    res.setHeader("Access-Control-Max-Age", "120");

	    if (req.getMethod().equals("OPTIONS")) {
	        res.setStatus(HttpServletResponse.SC_OK);
	        return;
	    }
//		System.out.println("Request Method = " + req.getMethod() + ", URL = " + req.getRequestURL());
//		String body = IOUtils.toString(req.getReader());
//        
//        System.out.println("Raw JSON: " + body);
        String path = req.getRequestURI();
        boolean excluded = path.equals("/signUp");

        if (!excluded) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                boolean tokenIsValid = false;

                for (Cookie cookie : cookies) {
                    if ("jwt_token".equals(cookie.getName())) {
                        String jwtToken = cookie.getValue();
                        try {
                            jwtUtils.validateToken(jwtToken);
                            tokenIsValid = true;
                            break;
                        } catch (ExpiredJwtException e) {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.getWriter().write("Token has expired");
                            return;
                        } catch (MalformedJwtException e) {
                            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            res.getWriter().write("Invalid token format");
                            return;
                        }
                    }
                }

                if (!tokenIsValid) {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.getWriter().write("Unauthorized");
                    return;
                }
            } else {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("Unauthorized");
                return;
            }
        }
        chain.doFilter(request, response);
		
	}

}
