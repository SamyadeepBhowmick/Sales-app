package com.cognizant.authorization.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cognizant.authorization.model.AuthUser;
import com.cognizant.authorization.pojo.UserCredentialDetails;
import com.cognizant.authorization.repository.AuthRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	AuthRepository authRepository;

	public void createUser(UserCredentialDetails authUser) {
		AuthUser user = new AuthUser();
		Date date = new Date();
		user.setName(authUser.getName());
		user.setAge(authUser.getAge());
		user.setEmail(authUser.getEmail());
		user.setPassword(authUser.getPassword());
		user.setCellNumber(authUser.getCellNumber());
		user.setDateOfRegistration(date);
		user.setLastLoginTime(date);
		authRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		AuthUser user = authRepository.findByEmail(email);
		return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	public ResponseEntity<AuthUser> updateUser(UserCredentialDetails authUser) {
		AuthUser user = authRepository.findByEmail(authUser.getEmail());
		if (user != null) {
			user.setName(authUser.getName());
			user.setAge(authUser.getAge());
			user.setEmail(authUser.getEmail());
			user.setPassword(authUser.getPassword());
			user.setCellNumber(authUser.getCellNumber());
			authRepository.save(user);
			return new ResponseEntity<AuthUser>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<AuthUser>(user, HttpStatus.NOT_FOUND);
		}
	}
	
	public void setTokenIn(String email,String token) {
		AuthUser user = authRepository.findByEmail(email);
		user.setToken(token);
		authRepository.save(user);
	}

}
