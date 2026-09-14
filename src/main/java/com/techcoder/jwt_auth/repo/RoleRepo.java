package com.techcoder.jwt_auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techcoder.jwt_auth.model.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
