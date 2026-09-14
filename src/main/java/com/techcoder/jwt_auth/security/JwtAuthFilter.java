package com.techcoder.jwt_auth.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.techcoder.jwt_auth.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Creating own JWT Authentication FilterChain 
 * 
 * */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		// Extract jwt auth header
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			// Not a valid Bearer token
			filterChain.doFilter(request, response);
			return;
		}
		
		// Contains jwt 
		String jwt = authHeader.substring(7);
		
		// Extract username
		String username = jwtUtils.extractUsername(jwt);
		
		// User not yet authenticated
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			// Early expiry check before hitting DB
			if (jwtUtils.isTokenExpired(jwt)) {
				filterChain.doFilter(request, response);
				return;
			}

			// Load user details from DB (roles included here)
			UserDetails userDB = userDetailsService.loadUserByUsername(username);
			
			// username not null and token is valid
			if (jwtUtils.validateToken(jwt, userDB)) {
				
				// register current user into SecurityContextHolder
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(userDB, null, userDB.getAuthorities());
				
				// setting the request details 
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Update the SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		
		// Continue FilterChain
		filterChain.doFilter(request, response);
	}
}
