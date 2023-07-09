package com.cognizant.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration class
 */
@Configuration
public class SecurityConfig {

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/**").permitAll().requestMatchers(HttpMethod.PUT, "/**").permitAll()
				.and().csrf().disable();
		return http.build();
	}
}
