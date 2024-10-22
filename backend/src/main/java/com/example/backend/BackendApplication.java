package com.example.backend;

import com.example.backend.entities.Affiliation;
import com.example.backend.services.CandidateService;
import com.example.backend.services.AffiliationService;
import com.example.backend.xmlreader.XmlReaderV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootApplication()
@RestController
public class BackendApplication {
	private List<Map<String,Object>> registrations = new ArrayList<>();
	public static final String storedUsersPath="backend/src/main/java/com/example/backend/storedUsers";
	private static final String FILENAME = "res/Kandidatenlijsten_TK2021_Amsterdam.eml.xml";
	private static final String OUTPUT_FILENAME = "backend/src/test/resources/output.txt";

	private final CandidateService candidateService;
	private final AffiliationService affiliationService;

	@Autowired
	public BackendApplication(CandidateService candidateService, AffiliationService affiliationService) {
		this.candidateService = candidateService;
		this.affiliationService = affiliationService;
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(BackendApplication.class, args);
		XmlReaderV2 xmlReader = new XmlReaderV2(applicationContext);

		String FILENAME = "res/Kandidatenlijsten_TK2021_Amsterdam.eml.xml";

		xmlReader.parseAndInsert("res/Kandidatenlijsten_TK2021_Bonaire.eml.xml");
		xmlReader.parseAndInsert("res/Kandidatenlijsten_TK2021_Amsterdam.eml.xml");
	}

	@GetMapping(path="/test", produces = "application/json")
	public Map<String,String> getTest(){
		Map<String, String> response = new HashMap<>();
		response.put("message","ok");
		response.put( "status","active");
		return response;
	}

	@PostMapping(path="/login")
	public ResponseEntity<HashMap<String,String>> loginUser(@RequestBody Map<String, Object>loginCredentials){
		HashMap<String,String> emailJSON = new HashMap<>();
		String emailInput = (String) loginCredentials.get("email");
		String passwordInput = (String) loginCredentials.get("password");

		try {
			File file = new File(storedUsersPath);
			if (!file.exists()) {
				return ResponseEntity.status(404).body(emailJSON);
			}

			Scanner myScanner = new Scanner(file);
			while (myScanner.hasNextLine()) {
				String line = myScanner.nextLine().trim();

				System.out.println("Reading line: " + line);

				if (!line.isEmpty()) {
					Map<String, String> storedUserData = parseUserData(line);
					System.out.println("Parsed data: " + storedUserData);

					String storedEmail = storedUserData.get("email");
					String storedPassword = storedUserData.get("password");

					System.out.println("Stored email: " + storedEmail);
					System.out.println("Stored password: " + storedPassword);

					if (storedEmail != null && storedEmail.equals(emailInput) &&
							storedPassword != null && storedPassword.equals(passwordInput)) {
						myScanner.close();
						System.out.println("Login successful!");
						emailJSON.put("email",storedEmail);
						return ResponseEntity.status(HttpStatus.OK).body(emailJSON);
					}
				}
			}
			myScanner.close();
			emailJSON.put("email","not found");
			return ResponseEntity.status(401).body(emailJSON);

		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
			e.printStackTrace();
			return ResponseEntity.status(500).body(emailJSON);
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return ResponseEntity.status(500).body(emailJSON);
		}
	}


	private Map<String, String> parseUserData(String userData) {
		Map<String, String> parsedData = new HashMap<>();
		String[] keyValuePairs = userData.replace("{", "").replace("}", "").split(", ");

		for (String pair : keyValuePairs) {
			String[] entry = pair.split("=", 2);
			if (entry.length == 2) {
				parsedData.put(entry[0], entry[1]);
			} else {
				System.out.println("Invalid key-value pair: " + pair);
			}
		}

		return parsedData;
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

//	@GetMapping(path="/test/path")
//	private Map<Long, Map<String, String>> getTestPath(){
//		Map<Long, Map<String, String>> response = new HashMap<>();
//		List<Affiliation> affiliations = affiliationService.getAllAffiliations();
//
//		for (Affiliation affiliation : affiliations) {
//			Map<String, String> object = new HashMap<>();
//
//			object.put("AffiliationName",affiliation.getAffiliationName());
//			object.put("MemberCount", String.valueOf(affiliation.getCandidates().size()));
//
//			response.put(affiliation.getAffiliationId(), object);
//		}
//		return response;
//	}

}
