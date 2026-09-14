package com.techcoder.jwt_auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techcoder.jwt_auth.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationEntryPoint entryPoint;

    // Whitelisted endpoints (no authentication required)
    private static final String[] WHITE_LISTED_URL = {
        "/login",
//        "/api/hello"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSec) throws Exception {

        // Disable CSRF for JWT-based APIs
        httpSec.csrf(csrf -> csrf.disable());

        // Stateless session since we use JWTs
        httpSec.sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Inject authentication provider
        httpSec.authenticationProvider(authenticationProvider);

        // Set custom entry point for handling auth exceptions
        httpSec.exceptionHandling(exception -> 
            exception.authenticationEntryPoint(entryPoint));

        // Authorization rules
        httpSec.authorizeHttpRequests(auth -> auth
            .requestMatchers(WHITE_LISTED_URL).permitAll()

            // Other user-only APIs
            .requestMatchers("/api/**").hasAuthority("USER")

            // All other requests require authentication
            .anyRequest().authenticated()
        );

        // Add JWT filter before Spring Security’s default auth filter
        httpSec.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSec.build();
    }
}
