package com.techcoder.jwt_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techcoder.jwt_auth.model.LoginRequest;
import com.techcoder.jwt_auth.service.LoginService;
import com.techcoder.jwt_auth.utils.JwtUtils;

@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req){
		return loginService.login(req);
	}
	
}
