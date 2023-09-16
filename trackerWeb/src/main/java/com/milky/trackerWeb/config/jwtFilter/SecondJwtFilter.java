package com.milky.trackerWeb.config.jwtFilter;

import java.io.IOException;
import java.util.Arrays;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class SecondJwtFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        // Paths that this filter should handle
        String[] includedPaths = { "/api/first", "/api/anotherFirst" };

        boolean included = Arrays.stream(includedPaths).anyMatch(path::startsWith);

        if (included) {
            // Your JWT logic for the first filter
        }

        // continue to the next filter
        chain.doFilter(request, response);
		
	}

   

}
