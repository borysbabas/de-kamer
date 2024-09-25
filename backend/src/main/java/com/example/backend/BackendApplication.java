package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootApplication
@RestController
public class BackendApplication {
	private List<Map<String,Object>> registrations = new ArrayList<>();
	public static final String storedUsersPath="backend/src/main/java/com/example/backend/storedUsers";


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
	@PostMapping(path="/login")
	public ResponseEntity<Boolean> loginUser(@RequestBody Map<String, Object>loginCredentials){
		try{
			System.out.println(loginCredentials);
			String inputEmail =(String)loginCredentials.get("email");
			String inputPassword=(String)loginCredentials.get("password");
			Scanner myScanner = new Scanner(storedUsersPath);
			while(myScanner.hasNextLine()){
				String variable = myScanner.nextLine();
				String[] storedUser = variable.split(",");
				String storedEmail = storedUser[0];
				String storedPassword=storedUser[1];

				if(storedEmail.equals(inputEmail)&& storedPassword.equals(inputPassword)){
					myScanner.close();
					return ResponseEntity.ok(true);
				}
			}
			myScanner.close();
			return ResponseEntity.status(401).body(false);

		}
		catch(Exception E){
			System.out.println("An error occured");
			E.printStackTrace();
			return ResponseEntity.status(500).body(false);

		}

	}

	@PostMapping(path="/register")
	private List<Map<String, Object>> registerUser(@RequestBody Map<String,Object>userData){
		registrations.add(userData);
		try{
			String toStringData=userData.toString();
			FileWriter newWriter = new FileWriter(storedUsersPath,true);
			newWriter.write("\n");
			newWriter.write(toStringData);
			newWriter.close();
		}catch(IOException e){
			System.out.println("an error occured");
			e.printStackTrace();
		}
		return registrations;
	}



}
