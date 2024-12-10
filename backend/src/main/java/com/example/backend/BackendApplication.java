package com.example.backend;

import com.example.backend.authentication.JWToken;
import com.example.backend.models.XmlFileModel;
import com.example.backend.xmlreader.FileDiscovery;
import com.example.backend.xmlreader.XmlStreamReaderV1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootApplication()
@RestController
public class BackendApplication {
    private final List<Map<String, Object>> registrations = new ArrayList<>();

    public static final String storedUsersPath = "backend/src/main/java/com/example/backend/storedUsers";
    private static final String XML_DIR = "res/";
    private static final boolean RUN_PARSER = true;
    public static final boolean ENTER_POLLING_STATION_VOTES = true;
    public static final boolean addZeroVotes = false;

    public BackendApplication() {
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        ApplicationContext applicationContext = SpringApplication.run(BackendApplication.class, args);
        JWToken token = new JWToken("JOHN DOE", 123L, "user");
        String jwt = token.encode("myApp", "mySecretKey", 3600);
        System.out.println(jwt);

        if (!RUN_PARSER) {
            return;
        }

        XmlStreamReaderV1 xmlStreamReader = new XmlStreamReaderV1(applicationContext);

        var allFiles = FileDiscovery.discoverFiles(XML_DIR);

        var startTime = System.nanoTime();
        for (ArrayList<XmlFileModel> allFile : allFiles) {
            if (allFile == null || allFile.isEmpty()) {
                continue;
            }
            for (XmlFileModel fileModel : allFile) {
                xmlStreamReader.parseFile(fileModel.filePath(), fileModel.fileType());
            }
        }
        var elapsedNanos = System.nanoTime() - startTime;
        // print time elapsed in seconds
        System.out.println("Elapsed time: " + (double) elapsedNanos / 1_000_000_000 + " s");
    }

    @GetMapping(path = "/test", produces = "application/json")
    public Map<String, String> getTest() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "ok");
        response.put("status", "active");
        return response;
    }


    @PostMapping(path = "/login")
    public ResponseEntity<HashMap<String, String>> loginUser(@RequestBody Map<String, Object> loginCredentials) {
        HashMap<String, String> emailJSON = new HashMap<>();
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
                        emailJSON.put("email", storedEmail);
                        return ResponseEntity.status(HttpStatus.OK).body(emailJSON);
                    }
                }
            }
            myScanner.close();
            emailJSON.put("email", "not found");
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

    @PostMapping(path = "/register")
    private List<Map<String, Object>> registerUser(@RequestBody Map<String, Object> userData) {
        registrations.add(userData);
        try {
            String toStringData = userData.toString();
            FileWriter newWriter = new FileWriter(storedUsersPath, true);
            newWriter.write("\n");
            newWriter.write(toStringData);
            newWriter.close();
        } catch (IOException e) {
            System.out.println("an error occured");
        }
        return registrations;
    }

}
