package com.techcoder.jwt_auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.techcoder.jwt_auth.model.Role;
import com.techcoder.jwt_auth.model.UserApp;
import com.techcoder.jwt_auth.repo.RoleRepo;
import com.techcoder.jwt_auth.repo.UserRepo;

@Component
public class AppRunner implements ApplicationRunner{
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Role role1 = new Role();
		role1.setRoleName("ADMIN");
		Role role2 = new Role();
		role2.setRoleName("USER");
		
		roleRepo.save(role1);
		roleRepo.save(role2);
		
		UserApp user1 = new UserApp();
		user1.setUsername("abc");
		user1.setPassword("123");
		user1.setRole(role1);
		UserApp user2 = new UserApp();
		user2.setUsername("xyz");
		user2.setPassword("456");
		user2.setRole(role2);
		UserApp user3 = new UserApp();
		user3.setUsername("abcd");
		user3.setPassword("1234");
		user3.setRole(role2);
		
		userRepo.save(user1);
		userRepo.save(user2);
		userRepo.save(user3);
	}

}
