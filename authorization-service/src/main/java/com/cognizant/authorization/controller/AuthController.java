package com.cognizant.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authorization.model.AuthUser;
import com.cognizant.authorization.pojo.UserCredentialDetails;
import com.cognizant.authorization.pojo.UserCredentials;
import com.cognizant.authorization.pojo.ValidatorCredentials;
import com.cognizant.authorization.service.AuthService;
import com.cognizant.authorization.util.JwtUtil;

@RestController
public class AuthController {

	@Autowired
	AuthService authService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/health")
	public String healthCheck() {
		return "Authorization service up and running...";
	}

	@PostMapping("/register")
	public ResponseEntity<String> createUser(@RequestBody UserCredentialDetails userCredentialDetails) {
		if (userCredentialDetails.getEmail() == null || userCredentialDetails.getPassword() == null)
			return new ResponseEntity<>("User Not Registered", HttpStatus.OK);
		else {
			authService.createUser(userCredentialDetails);
			return new ResponseEntity<>("User Registered", HttpStatus.OK);
		}
	}

	@PostMapping("/login")
	public String loginwithjwt(@RequestBody UserCredentials userCredentials) {
		try {
			UserDetails user = authService.loadUserByUsername(userCredentials.getEmail());
			if (user.getPassword().equals(userCredentials.getPassword())) {
				String token = jwtUtil.generateToken(userCredentials.getEmail());
				System.out.println(token);
				authService.setTokenIn(userCredentials.getEmail(),token);
				return token;
			} else {
				System.out.println("password wrong");
			}
		} catch (Exception e) {
			System.out.println("exception occurred");
		}
		return "";
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody UserCredentialDetails userCredentialDetails,
			@RequestHeader(name = "Authorization") String token1) {
		String token = token1.substring(7);
		try {
			UserDetails user = authService.loadUserByUsername(jwtUtil.extractUsername(token));

			if (jwtUtil.validateToken(token, user)) {
				System.out.println("=================Inside Validate==================");
				ResponseEntity<AuthUser> res = authService.updateUser(userCredentialDetails);
				if (res.getBody() == null)
					return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
				return new ResponseEntity<>(res.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}

	}

	@GetMapping("/validate")
	public ResponseEntity<?> validate(@RequestHeader(name = "Authorization") String token1) {
		String token = token1.substring(7);
		try {
			UserDetails user = authService.loadUserByUsername(jwtUtil.extractUsername(token));
			ValidatorCredentials validatorCredentials=new ValidatorCredentials();
			validatorCredentials.setEmail(user.getUsername());
			if (jwtUtil.validateToken(token, user)) {
				System.out.println("=================Inside Validate==================");
				validatorCredentials.setValid(true);
				return new ResponseEntity<>(validatorCredentials, HttpStatus.OK);
			} else {
				validatorCredentials.setValid(false);
				return new ResponseEntity<>(validatorCredentials, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Exception occurred", HttpStatus.FORBIDDEN);
		}
	}
}
