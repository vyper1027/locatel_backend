package com.ventas.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	    .csrf(csrf -> csrf.disable())
	    .cors(cors -> cors.disable())
	    .sessionManagement( sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    	.authorizeHttpRequests((requests) -> {
    		requests.requestMatchers(
    				"/auth/login", 
    				"/swagger-ui/**", 
    				"/swagger-ui.html/**", 
    				"/swagger-resources/**", 
    				"/api-docs/**", 
    				"/swagger-ui-custom/**", 
    				"/error/**",
    				"/usuario/**",
    				"/**").permitAll();
    		requests.requestMatchers(HttpMethod.POST, "/usuario").permitAll().anyRequest().authenticated();
    	});

	    return http.build();
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
