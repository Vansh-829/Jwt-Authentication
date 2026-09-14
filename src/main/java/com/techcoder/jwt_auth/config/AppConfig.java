package com.techcoder.jwt_auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.techcoder.jwt_auth.repo.UserRepo;

@Configuration
@SuppressWarnings("deprecation") // Only because NoOpPasswordEncoder is used
public class AppConfig {
	
	@Autowired
	private UserRepo userRepo;

	// Custom UserDetailsService that loads user from DB using username
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepo.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Username Not Found: " + username));
	}
	
	// WARNING: NoOpPasswordEncoder is insecure and only for testing.
	@Bean
	public PasswordEncoder passEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	// DAO-based AuthenticationProvider
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passEncoder());
		provider.setUserDetailsService(userDetailsService());
		return provider;	
	}
	
	// Get AuthenticationManager from config
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
