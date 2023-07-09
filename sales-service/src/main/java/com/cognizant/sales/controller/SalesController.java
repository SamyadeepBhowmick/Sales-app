package com.cognizant.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.sales.exception.InvalidTokenException;
import com.cognizant.sales.model.ItemDetails;
import com.cognizant.sales.pojo.ValidatorCredentials;
import com.cognizant.sales.service.SalesSerivce;
import com.cognizant.sales.service.TokenCheckService;

@RestController
public class SalesController {

	@Autowired
	SalesSerivce salesSerivce;

	@Autowired
	TokenCheckService tokenCheckService;

	@Value("${authservice.url}")
	private String validateURL;

	@GetMapping("/health")
	public String healthCheck() {
		return "Feed service up and running...";
	}

	ValidatorCredentials validatorCredentials;

	@GetMapping("/allitems")
	public ResponseEntity<Object> getAllItems(@RequestHeader("Authorization") String token)
			throws InvalidTokenException {
		validatorCredentials = tokenCheckService.checkToken(token);
		if (validatorCredentials.isValid()) {
			return salesSerivce.getAllItems();
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}
	
	@GetMapping("/allcartitems")
	public ResponseEntity<Object> getAllCartItems(@RequestHeader("Authorization") String token)
			throws InvalidTokenException {
		validatorCredentials = tokenCheckService.checkToken(token);
		if (validatorCredentials.isValid()) {
			return salesSerivce.getAllCartItems(validatorCredentials.getEmail());
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}
	
	@GetMapping("/alltransactions")
	public ResponseEntity<Object> getAllTransactions(@RequestHeader("Authorization") String token)
			throws InvalidTokenException {
		validatorCredentials = tokenCheckService.checkToken(token);
		if (validatorCredentials.isValid()) {
			return salesSerivce.getAllTransactions(validatorCredentials.getEmail());
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}
	
	@PostMapping("/additem")
	public ResponseEntity<Object> addItem(@RequestHeader("Authorization") String token, @RequestBody ItemDetails itemDetails)
			throws InvalidTokenException {
		validatorCredentials = tokenCheckService.checkToken(token);
		if (validatorCredentials.isValid()) {
			return salesSerivce.addItem(validatorCredentials.getEmail(),itemDetails);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}
	
	@PostMapping("/placeorder")
	public ResponseEntity<Object> placeOrder(@RequestHeader("Authorization") String token)
			throws InvalidTokenException {
		validatorCredentials = tokenCheckService.checkToken(token);
		if (validatorCredentials.isValid()) {
			return salesSerivce.placeOrder(validatorCredentials.getEmail());
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}

	@PostMapping("/addtocart/{itemId}/{quantity}")
	public ResponseEntity<Object> addToCart(@RequestHeader("Authorization") String token,
			@PathVariable String itemId, @PathVariable int quantity) throws InvalidTokenException {
		validatorCredentials = tokenCheckService.checkToken(token);
		if (validatorCredentials.isValid()) {
			return salesSerivce.addToCart(validatorCredentials.getEmail(), itemId, quantity);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}
	
	@DeleteMapping("/removefromcart/{id}")
	public ResponseEntity<Object> removeFromCart(@RequestHeader("Authorization") String token,
			@PathVariable String id) throws InvalidTokenException {
		validatorCredentials = tokenCheckService.checkToken(token);
		if (validatorCredentials.isValid()) {
			return salesSerivce.removeFromCart(id);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}
	
	
}