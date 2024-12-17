package com.example.backend;

import com.example.backend.authentication.JWToken;
import com.example.backend.models.XmlFileModel;
import com.example.backend.xmlreader.FileDiscovery;
import com.example.backend.xmlreader.XmlStreamReaderV1;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.security.Key;
import java.util.*;

@SpringBootApplication()
@RestController
public class BackendApplication {
    private static final String XML_DIR = "res/";
    private static final boolean RUN_PARSER = false;
    public static final boolean ENTER_POLLING_STATION_VOTES = true;
    public static final boolean addZeroVotes = false;

    public BackendApplication() {
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        ApplicationContext applicationContext = SpringApplication.run(BackendApplication.class, args);
        String issuer = "private company";
        String passphrase = "secretPassphrase"; // Dit kan een langere passphrase zijn
        String role = "user"; // Dit kan 'admin' zijn voor adminrechten

        String callName = "gebruikertest";
        Long accountId= 1L;

        JWToken token = new JWToken(callName, accountId,role);
        String jwtToken = token.encode(issuer, passphrase, 3600);
        System.out.println(jwtToken);

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
}
