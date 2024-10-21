package com.example.backend;

import com.example.backend.entities.Candidate;
import com.example.backend.entities.Party;
import com.example.backend.repositories.CandidateRepository;
import com.example.backend.repositories.PartyRepository;
import com.example.backend.services.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

	private final CandidateRepository candidateRepository;
	private final PartyService partyService;

	@Autowired
	public BackendApplication(CandidateRepository candidateRepository, PartyService partyService) {
		this.candidateRepository = candidateRepository;
		this.partyService = partyService;
	}

	@PostMapping(path="/party/add")
	public Party addNewParty (@RequestBody Party party) {
		partyService.addParty(party);
		return party;
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(BackendApplication.class, args);

		PartyService partyService = (PartyService) applicationContext.getBean(PartyService.class);
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(FILENAME));

			doc.getDocumentElement().normalize();

			Element root = doc.getDocumentElement();

			System.out.println("Root element: " + root.getNodeName());

			// Prepare the output file
			int localId = 0;
			FileWriter writer = new FileWriter(OUTPUT_FILENAME);

			// Get the list of affiliations
			NodeList affiliationList = doc.getElementsByTagName("Affiliation");

			// Loop through the list of affiliations
			for (int i = 0; i < affiliationList.getLength(); i++) {
				System.out.println("Affiliation: " + i);
				Node affiliationNode = affiliationList.item(i);

				if (affiliationNode.getNodeType() == Node.ELEMENT_NODE) {
					Element affiliaitionElement = (Element) affiliationNode;

					String affiliationIdentifier = affiliaitionElement.getElementsByTagName("AffiliationIdentifier").item(0).getTextContent().trim();
					Party party = new Party((long) i, affiliationIdentifier);

					partyService.addParty(party);

					writer.write("Affiliation Identifier: " + affiliationIdentifier + "\n");
					System.out.println("Affiliation Identifier: " + affiliationIdentifier);

					NodeList candidateList = affiliaitionElement.getElementsByTagName("Candidate");
					System.out.println("Candidate List Length: " + candidateList.getLength());
					writer.write("Candidate List Length: " + candidateList.getLength() + "\n");

					// Loop through the list of candidates
					for (int j = 0; j < candidateList.getLength(); j++) {
						Node candidateNode = candidateList.item(j);
						String NamePrefix = null;
						String gender = null;
						String firstName = null;

						if (candidateNode != null && candidateNode.getNodeType() == Node.ELEMENT_NODE) {
							Element candidateElement = (Element) candidateNode;

							Node initialsNode = candidateElement.getElementsByTagName("xnl:NameLine").item(0);
							Node firstNameNode = candidateElement.getElementsByTagName("xnl:FirstName").item(0);
							Node namePrefixNode = candidateElement.getElementsByTagName("xnl:NamePrefix").item(0);
							Node lastNameNode = candidateElement.getElementsByTagName("xnl:LastName").item(0);
							Node genderNode = candidateElement.getElementsByTagName("Gender").item(0);
							Node localityNode = candidateElement.getElementsByTagName("xal:LocalityName").item(0);

							try {
								String initials = initialsNode.getTextContent();
								try {
									firstName = firstNameNode.getTextContent();
								} catch (Exception e) {
									firstName = "Not Specified";
								}
								try {
									NamePrefix = namePrefixNode.getTextContent();
								} catch (Exception e) {
									//do nothing
								}
								String lastName = lastNameNode.getTextContent();
								try {
									gender = genderNode.getTextContent();
								} catch (Exception e) {
									gender = "Not Specified";
								}
								String locality = localityNode.getTextContent();

								writer.write("Affiliation Identifier: " + affiliationIdentifier + "\n");
								writer.write("\n");
								writer.write("Candidate: " + j + "\n");
								writer.write("Initials: " + initials + "\n");
								writer.write("First Name: " + firstName + "\n");
								if (NamePrefix != null) {
									writer.write("Last Name: " + NamePrefix + " " + lastName + "\n");
								} else {
									writer.write("Last Name: " + lastName + "\n");
								}
								writer.write("Gender: " + gender + "\n");
								writer.write("Locality: " + locality + "\n");
								writer.write("\n");


							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

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



}
