package com.techcoder.jwt_auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techcoder.jwt_auth.model.UserApp;

@Repository
public interface UserRepo extends JpaRepository<UserApp, Integer>{

	Optional<UserApp> findByUsername(String username);
}
