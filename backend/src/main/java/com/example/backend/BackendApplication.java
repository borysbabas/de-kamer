package com.example.backend;

import com.example.backend.xmlreader.XMLTagNames;
import com.example.backend.xmlreader.XmlStreamReaderV1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootApplication()
@RestController
public class BackendApplication {
	private final List<Map<String,Object>> registrations = new ArrayList<>();

    public static final String storedUsersPath="backend/src/main/java/com/example/backend/storedUsers";
	private static final String PATH_PREFIX = "res/";
	private static final Set<String> allFiles = Set.of(
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Amsterdam.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Arnhem.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Assen.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Bonaire.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Den_Helder.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Dordrecht.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Groningen.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Haarlem.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Leeuwarden.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Leiden.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Lelystad.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Maastricht.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Middelburg.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Nijmegen.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Rotterdam.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_s-Gravenhage.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_s-Hertogenbosch.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Tilburg.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Utrecht.eml.xml",
            PATH_PREFIX + "Kandidatenlijsten_TK2021_Zwolle.eml.xml"
    );

	public BackendApplication() {
    }

	public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
		ApplicationContext applicationContext = SpringApplication.run(BackendApplication.class, args);
		XmlStreamReaderV1 xmlStreamReader = new XmlStreamReaderV1(applicationContext);

		long startTime = System.nanoTime();
		xmlStreamReader.parseFile("res/Verkiezingsdefinitie_TK2021.eml.xml", XMLTagNames.FILE_TYPE.ELECTION_TREE);
		long stopTime = System.nanoTime();
		System.out.print("XMLStreamReader Time: ");
		System.out.println(stopTime - startTime);

		startTime = System.nanoTime();
        for (String filename : allFiles) {
            xmlStreamReader.parseFile(filename, XMLTagNames.FILE_TYPE.CANDIDATE_LIST);
        }
        stopTime = System.nanoTime();
		System.out.print("XMLReader Time: ");
		System.out.println(stopTime - startTime);

		startTime = System.nanoTime();
		xmlStreamReader.parseFile("res/Totaaltelling_TK2021.eml.xml", XMLTagNames.FILE_TYPE.TOTAL_COUNT);
		stopTime = System.nanoTime();
		System.out.print("XMLStreamReader Time: ");
		System.out.println(stopTime - startTime);

		startTime = System.nanoTime();
		xmlStreamReader.parseFile("res/Telling_TK2021_kieskring_Amsterdam.eml.xml", XMLTagNames.FILE_TYPE.VOTES_CONSTITUENCY);
		xmlStreamReader.parseFile("res/Telling_TK2021_kieskring_Zwolle.eml.xml", XMLTagNames.FILE_TYPE.VOTES_CONSTITUENCY);
		stopTime = System.nanoTime();
		System.out.print("XMLStreamReader Time: ");
		System.out.println(stopTime - startTime);

		startTime = System.nanoTime();
		xmlStreamReader.parseFile("res/Gemeente tellingen/01 Groningen/Telling_TK2021_gemeente_Eemsdelta.eml.xml", XMLTagNames.FILE_TYPE.VOTES_MUNICIPALITY);
		stopTime = System.nanoTime();
		System.out.print("XMLStreamReader Time: ");
		System.out.println(stopTime - startTime);
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
			return ResponseEntity.status(500).body(emailJSON);
		} catch (Exception e) {
			System.out.println("An error occurred.");
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
		}
		return registrations;
	}

}
