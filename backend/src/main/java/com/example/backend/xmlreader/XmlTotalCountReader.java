package com.example.backend.xmlreader;

import com.example.backend.entities.AffiliationVotes;
import com.example.backend.entities.Candidate;
import com.example.backend.entities.CountryVotes;
import com.example.backend.services.AffiliationService;
import com.example.backend.services.AffiliationVotesService;
import com.example.backend.services.CandidateService;
import com.example.backend.services.CountryVotesService;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlTotalCountReader {
    private final AffiliationService affiliationService;
    private final AffiliationVotesService affiliationVotesService;
    private final CandidateService candidateService;
    private final CountryVotesService countryVotesService;

    public XmlTotalCountReader(
            AffiliationService affiliationService,
            AffiliationVotesService affiliationVotesService,
            CandidateService candidateService,
            CountryVotesService countryVotesService
    ) {
        this.affiliationService = affiliationService;
        this.affiliationVotesService = affiliationVotesService;
        this.candidateService = candidateService;
        this.countryVotesService = countryVotesService;
    }

    public void read(XMLStreamReader reader) throws XMLStreamException {
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
        boolean isAffiliation = false;
        Long affiliationId = null;
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
                            affiliationId = Long.parseLong(reader.getAttributeValue(null, XMLTagNames.ID));
                            isAffiliation = true;
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.SELECTION)) {
                        if (isAffiliation) {
                            AffiliationVotes affiliationVotes = new AffiliationVotes();
                            affiliationVotes.setAffiliation(affiliationService.findAffiliationByAffiliationId(affiliationId));
                            affiliationVotes.setAffiliationVotes(votes);
                            affiliationVotesService.addVote(affiliationVotes);
                            return;
                        }
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
