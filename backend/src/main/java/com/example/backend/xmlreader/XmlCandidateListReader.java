package com.example.backend.xmlreader;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import com.example.backend.entities.Constituency;
import com.example.backend.services.AffiliationService;
import com.example.backend.services.CandidateService;
import com.example.backend.services.ConstituencyService;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Objects;

public class XmlCandidateListReader {
    private final AffiliationService affiliationService;
    private final CandidateService candidateService;
    private final ConstituencyService constituencyService;

    private final Constituency constituency = new Constituency();
    private Affiliation affiliation;


    public XmlCandidateListReader(
            AffiliationService affiliationService,
            CandidateService candidateService,
            ConstituencyService constituencyService
    ) {
        this.affiliationService = affiliationService;
        this.candidateService = candidateService;
        this.constituencyService = constituencyService;
    }

    public void read(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals(XMLTagNames.CONTEST)) {
                    readContest(reader);
                }
            }
        }
    }

    public void readContest(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                switch (elementName) {
                    case XMLTagNames.CONTEST_ID:
                        readContestId(reader);
                        break;
                    case XMLTagNames.AFFILIATION:
                        readAffiliation(reader);
                        break;
                }
            }
        }
    }

    private void readContestId(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            String elementName = reader.getLocalName();
            switch (elementName) {
                case XMLTagNames.CONTEST_ID:
                    constituency.setConstituencyId(Long.valueOf(reader.getAttributeValue(0)));
                    break;
                case XMLTagNames.CONTEST_NAME:
                    constituency.setConstituencyName(reader.getElementText());
            }
            int eventType = reader.next();
            if (eventType == XMLStreamReader.END_ELEMENT) {
                constituencyService.addConstituency(constituency);
                return;
            }
        }
    }

    private void readAffiliation(XMLStreamReader reader) throws XMLStreamException {
        this.affiliation = new Affiliation();
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                switch (elementName) {
                    case XMLTagNames.AFFILIATION_ID:
                        Long affiliationId = Long.parseLong(reader.getAttributeValue(null, XMLTagNames.ID));
                        affiliation.setAffiliationId(affiliationId);
                        break;
                    case XMLTagNames.REGISTERED_NAME:
                        affiliation.setAffiliationName(reader.getElementText());
                        break;
                    case XMLTagNames.TYPE:
                        affiliation.setIdenticalLists(Objects.equals(reader.getElementText(), XMLTagNames.IDENTICAL_LISTS));
                        if (affiliationService.findAffiliationNameByAffiliationId(affiliation.getAffiliationId()) != null) {
                            if (affiliation.isIdenticalLists()) return;
                        }
                        affiliationService.addAffiliation(affiliation);
                        break;
                    case XMLTagNames.CANDIDATE:
                        readCandidate(reader);
                        break;
                }
            }
            if (eventType == XMLStreamReader.END_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals(XMLTagNames.AFFILIATION)) {
                    return;
                }
            }
        }
    }

    private void readCandidate(XMLStreamReader reader) throws XMLStreamException {
        Candidate candidate = new Candidate(this.affiliation, this.constituency.getConstituencyId());

        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                switch (elementName) {
                    case XMLTagNames.CANDIDATE_ID:
                        candidate.setMemberId(Long.valueOf(reader.getAttributeValue(0)));
                        break;
                    case XMLTagNames.NAME_LINE_INITIALS:
                        candidate.setInitials(reader.getElementText());
                        break;
                    case XMLTagNames.FIRST_NAME:
                        candidate.setFirstName(reader.getElementText());
                        break;
                    case XMLTagNames.LAST_NAME_PREFIX:
                        candidate.setLastNamePrefix(reader.getElementText());
                        break;
                    case XMLTagNames.LAST_NAME:
                        candidate.setLastName(reader.getElementText());
                        break;
                    case XMLTagNames.GENDER:
                        candidate.setGender(reader.getElementText());
                        break;
                    case XMLTagNames.LOCALITY_NAME:
                        candidate.setLocality(reader.getElementText());
                        break;
                }
            }
            if (eventType == XMLStreamReader.END_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals(XMLTagNames.CANDIDATE)) {
                    candidate.setShortCode(candidateService.toShortCode(candidate));
//                    if (candidate.equals(candidateService.findByShortCodeAndConstituencyId(candidate.getShortCode(), constituency.getConstituencyId()))) {
//                        return;
//                    }
                    candidateService.addCandidate(candidate);
                    return;
                }
            }
        }
    }

}
