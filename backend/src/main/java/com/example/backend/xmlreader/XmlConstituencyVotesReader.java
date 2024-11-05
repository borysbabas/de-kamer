package com.example.backend.xmlreader;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import com.example.backend.models.VoteModel;
import com.example.backend.services.CandidateService;
import com.example.backend.services.ConstituencyService;
import com.example.backend.services.ConstituencyVotesService;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static com.example.backend.xmlreader.XmlStreamReaderV1.readSelection;

public class XmlConstituencyVotesReader {
    private final CandidateService candidateService;
    private final ConstituencyService constituencyService;
    private final ConstituencyVotesService constituencyVotesService;

    private Constituency constituency;
    private Affiliation affiliation;

    public XmlConstituencyVotesReader(
            CandidateService candidateService,
            ConstituencyService constituencyService,
            ConstituencyVotesService constituencyVotesService
    ) {
        this.candidateService = candidateService;
        this.constituencyService = constituencyService;
        this.constituencyVotesService = constituencyVotesService;
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
                        VoteModel voteModel = readSelection(reader);
                        if (voteModel == null) continue;

                        Long affiliationId = voteModel.getAffiliationId();
                        if (affiliationId != null) {
                            affiliation = new Affiliation();
                            affiliation.setAffiliationId(affiliationId);
                            continue;
                        }
                        Long candidateId = voteModel.getCandidateId();
                        if (candidateId != null) {
                            ConstituencyVotes constituencyVotes = new ConstituencyVotes();
                            constituencyVotes.setConstituency(constituency);
                            constituencyVotes.setCandidate(candidateService.getCandidateByMemberIdAndAffiliation(candidateId, affiliation));
                            constituencyVotes.setTotalVotes(voteModel.getVotes());
                            constituencyVotes.setAffiliation(affiliation);
                            constituencyVotesService.addConstituencyVotes(constituencyVotes);
                        }
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.TOTAL_VOTES)) {
                        return;
                    }
                    break;
            }
        }
    }
}
