package com.example.backend.xmlreader;

import com.example.backend.entities.Candidate;
import com.example.backend.entities.CountryVotes;
import com.example.backend.services.CandidateService;
import com.example.backend.services.CountryVotesService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Component
public class XmlStreamReaderV1 {
    private final CandidateService candidateService;
    private final CountryVotesService countryVotesService;

    public XmlStreamReaderV1(ApplicationContext applicationContext) {
        this.candidateService = applicationContext.getBean(CandidateService.class);
        this.countryVotesService = applicationContext.getBean(CountryVotesService.class);
    }

    public void parseFile(String filename) throws FileNotFoundException, XMLStreamException {
        InputStream raw = new FileInputStream(filename);
        readFromXml(raw);
    }

    private void readFromXml(InputStream inputStream) throws XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;

        try {
            reader = inputFactory.createXMLStreamReader(inputStream);
            readDocument(reader);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private void readDocument(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals(XMLTagNames.TOTAL_VOTES)) {
                    readTotalVotes(reader);
                }
            }
        }
    }

    private void readTotalVotes(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    if (elementName.equals(XMLTagNames.SELECTION)) {
                        readSelection(reader);
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.TOTAL_VOTES)) {
                        return;
                    }
            }
        }
    }

    private void readSelection(XMLStreamReader reader) throws XMLStreamException {
        String shortCode = null;
        long votes = 0;
        while (reader.hasNext()) {
            CountryVotes countryVotes = new CountryVotes();

            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();

                    switch (elementName) {
                        case XMLTagNames.CANDIDATE_IDENTIFIER:
                            shortCode = reader.getAttributeValue(null, XMLTagNames.SHORT_CODE);
                            break;
                        case XMLTagNames.VALID_VOTES:
                            votes = Long.parseLong(reader.getElementText());

                            break;
                        case XMLTagNames.AFFILIATION_IDENTIFIER:
                            return;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.SELECTION)) {

                        Candidate candidate = new Candidate(candidateService.getFirstByShortCode(shortCode));
                        countryVotes.setCandidate(candidate);
                        countryVotes.setTotalVotes(votes);

                        countryVotesService.addCountryVote(countryVotes);
                        return;
                    }
            }
        }
    }
}
