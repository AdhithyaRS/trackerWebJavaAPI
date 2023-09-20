package com.milky.trackerWeb.config;

import java.util.Random;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.milky.trackerWeb.config.jwtFilter.FirstJwtFilter;
import com.milky.trackerWeb.config.jwtFilter.SecondJwtFilter;
import com.milky.trackerWeb.service.JwtUtils;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Random random() {
        return new Random();
    }
    
    @Bean
    public FilterRegistrationBean<FirstJwtFilter> firstJwtFilter(JwtUtils jwtUtils) {
        FilterRegistrationBean<FirstJwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FirstJwtFilter(jwtUtils));
        registrationBean.addUrlPatterns("/signUp/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SecondJwtFilter> secondJwtFilter(JwtUtils jwtUtils) {
        FilterRegistrationBean<SecondJwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SecondJwtFilter(jwtUtils));
        registrationBean.addUrlPatterns("/signIn/*");
        registrationBean.setOrder(2); // This sets the order in which filters are invoked
        return registrationBean;
    }
}
 