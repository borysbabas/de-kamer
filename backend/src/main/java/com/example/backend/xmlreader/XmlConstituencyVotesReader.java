package com.example.backend.xmlreader;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import com.example.backend.services.CandidateService;
import com.example.backend.services.ConstituencyService;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlConstituencyVotesReader {
    private final CandidateService candidateService;
    private final ConstituencyService constituencyService;

    private Constituency constituency;
    private Affiliation affiliation;

    public XmlConstituencyVotesReader(CandidateService candidateService, ConstituencyService constituencyService) {
        this.candidateService = candidateService;
        this.constituencyService = constituencyService;
    }

    public void read(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                if (reader.getLocalName().equals(XMLTagNames.CONTEST)) {
                    readContest(reader);
                }
            }
        }
    }

    private void readContest(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case XMLTagNames.CONTEST_ID:
                            Long constituencyId = Long.parseLong(reader.getAttributeValue(0));
                            constituency = new Constituency(
                                    constituencyService.findConstituencyById(constituencyId)
                            );
                            continue;
                        case XMLTagNames.TOTAL_VOTES:
                            readTotalVotes(reader);
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.CONTEST)) {
                        return;
                    }
                    break;
            }
        }
    }

    private void readTotalVotes(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.SELECTION)) {
                        readSelection(reader);
                    }
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.TOTAL_VOTES)) {
                        return;
                    }
            }
        }
    }

    private void readSelection(XMLStreamReader reader) throws XMLStreamException {
        ConstituencyVotes constituencyVotes = new ConstituencyVotes();
        constituencyVotes.setConstituency(constituency);
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case XMLTagNames.AFFILIATION_ID:
                            affiliation = new Affiliation();
                            affiliation.setAffiliationId(Long.parseLong(reader.getAttributeValue(0)));
                            return;
                        case XMLTagNames.CANDIDATE_ID:
                            Long candidateId = Long.parseLong(reader.getAttributeValue(0));
                            constituencyVotes.setCandidate(candidateService.getCandidateByIdAndAffiliationId(candidateId, affiliation));
                            break;
                        case XMLTagNames.VALID_VOTES:
                            Long votes = Long.parseLong(reader.getElementText());
                            constituencyVotes.setTotalVotes(votes);
                            break;
                    }
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.SELECTION)) {
                        System.out.println(constituencyVotes.getConstituency().getConstituencyName() + ", " + constituencyVotes.getCandidate().getLastName() + ", " + constituencyVotes.getTotalVotes());
                        return;
                    }
            }
        }
    }
}
