package com.techcoder.jwt_auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techcoder.jwt_auth.model.LoginRequest;
import com.techcoder.jwt_auth.model.LoginResponse;
import com.techcoder.jwt_auth.utils.JwtUtils;

@Service
public class LoginService {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationProvider authenticationProvider;

    public ResponseEntity<?> login(LoginRequest req) {

        try {
            // fetch user from DB
            UserDetails userDb = userDetailsService.loadUserByUsername(req.getUsername());

            // validate password
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());

            authenticationProvider.authenticate(authToken);

            // generate JWT
            String token = jwtUtils.generateToken(userDb);

            return ResponseEntity.ok(new LoginResponse(token));
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("User not found");
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
