package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class BackendApplication {
	private List<Map<String,Object>> registrations = new ArrayList<>();


	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@GetMapping(path="/test", produces = "application/json")
	public Map<String,String> getTest(){
		Map<String, String> response = new HashMap<>();
		response.put("message","ok");
		response.put( "status","active");
		return response;
	}
	@GetMapping(path="/login" ,produces="application/json")
	public ResponseEntity<Boolean> loginUser(@RequestBody Map<String, Object>loginCredentials){
		System.out.println(loginCredentials);
		if(registrations.contains(loginCredentials)){
			return ResponseEntity.ok(true);

		}else{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}



	}

	@PostMapping(path="/register")
	private List<Map<String, Object>> registerUser(@RequestBody Map<String,Object>userData){
		registrations.add(userData);
		System.out.println(userData);
		return registrations;
	}



}
