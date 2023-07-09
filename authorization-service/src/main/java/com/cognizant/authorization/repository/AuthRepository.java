package com.cognizant.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.authorization.model.AuthUser;

@Repository
public interface AuthRepository extends JpaRepository<AuthUser, Integer> {
	public AuthUser findByEmail(String email);
}
