package com.techcoder.jwt_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techcoder.jwt_auth.utils.JwtUtils;

@RestController
@RequestMapping("/api")
public class DemoController {
	
	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/hello")
	public String hello(@RequestHeader("Authorization") String authHeader) {
		String username = jwtUtils.extractUsername(authHeader.substring(7));
		return "Server says hello to : " + username ;
	}
}
