package com.cognizant.sales.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cognizant.sales.pojo.ValidatorCredentials;

@Service
public class TokenCheckService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${authservice.url}")
	private String validateURL;
	
	public ValidatorCredentials checkToken(String token) {
		ValidatorCredentials validatorCredentials=new ValidatorCredentials();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		validatorCredentials= restTemplate.exchange(validateURL+"/validate", HttpMethod.GET, entity, ValidatorCredentials.class).getBody();
		return validatorCredentials;
		
	}

}
