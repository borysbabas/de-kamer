package com.example.backend.xmlreader;

import com.example.backend.entities.*;
import com.example.backend.models.VoteModel;
import com.example.backend.services.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static com.example.backend.xmlreader.XmlStreamReaderV1.readSelection;

public class XmlMunicipalityVotesReader {
    private final ConstituencyService constituencyService;
    private final MunicipalityVotesService municipalityVotesService;
    private final CandidateService candidateService;
    private final PollingStationService pollingStationService;
    private final PollingStationVotesService pollingStationVotesService;

    private Municipality municipality;
    private Affiliation affiliation;
    private PollingStation pollingStation;

    public XmlMunicipalityVotesReader(
            ConstituencyService constituencyService,
            MunicipalityVotesService municipalityVotesService,
            CandidateService candidateService,
            PollingStationService pollingStationService,
            PollingStationVotesService pollingStationVotesService
    ) {
        this.constituencyService = constituencyService;
        this.municipalityVotesService = municipalityVotesService;
        this.candidateService = candidateService;
        this.pollingStationService = pollingStationService;
        this.pollingStationVotesService = pollingStationVotesService;
    }

    public void read(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case XMLTagNames.MANAGING_AUTHORITY:
                            readManagingAuthority(reader);
                            break;
                        case XMLTagNames.CONTEST:
                            readContest(reader);
                            break;
                    }
                    break;
            }
        }
    }

    private void readManagingAuthority(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.AUTHORITY_ID)) {
                        Long authorityId = Long.parseLong(reader.getAttributeValue(null, XMLTagNames.ID));
                        municipality = new Municipality();
                        municipality.setMunicipalityId(authorityId);
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.MANAGING_AUTHORITY)) {
                        return;
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
                            Long contestId = Long.parseLong(reader.getAttributeValue(null, XMLTagNames.ID));
                            municipality.setConstituency(constituencyService.findConstituencyById(contestId));
                            continue;
                        case XMLTagNames.TOTAL_VOTES:
                            readTotalVotes(reader);
                            break;
                        case XMLTagNames.REPORTING_UNIT_VOTES:
                            readReportingUnitVotes(reader);
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.CONTEST)) {
                        return;
                    }
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
                        if (voteModel == null) {
                            continue;
                        }

                        Long affiliationId = voteModel.getAffiliationId();
                        if (affiliationId != null) {
                            affiliation = new Affiliation();
                            affiliation.setAffiliationId(affiliationId);
                            continue;
                        }

                        Long candidateId = voteModel.getCandidateId();
                        if (candidateId != null) {
                            MunicipalityVotes municipalityVote = new MunicipalityVotes();
                            municipalityVote.setCandidate(candidateService.getCandidateByIdAndAffiliation(candidateId, affiliation));
                            municipalityVote.setMunicipality(municipality);
                            municipalityVote.setAffiliation(affiliation);
                            municipalityVote.setConstituency(municipality.getConstituency());
                            municipalityVote.setTotalVotes(voteModel.getVotes());
                            municipalityVotesService.addMunicipalityVote(municipalityVote);
                        }
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.TOTAL_VOTES)) {
                        return;
                    }
            }
        }
    }

    private void readReportingUnitVotes(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case XMLTagNames.REPORTING_UNIT_ID:
                            String reportingUnitId = reader.getAttributeValue(null, XMLTagNames.ID);
                            String reportingUnitName = reader.getElementText();
                            Long pollingStationId = Long.parseLong(reportingUnitId.replaceFirst(".*::SB", ""));
                            String pollingStationZip = reportingUnitName.replaceAll(".*\\(postcode:|\\).*| ", "");
                            String pollingStationName = reportingUnitName.replaceAll("\\(.*", "");
                            pollingStation = new PollingStation(
                                    pollingStationId,
                                    pollingStationName,
                                    pollingStationZip
                            );
                            pollingStation.setMunicipality(municipality);
                            pollingStationService.addPollingStation(pollingStation);
                            break;
                        case XMLTagNames.SELECTION:
                            VoteModel voteModel = readSelection(reader);

                            if (voteModel == null) {
                                continue;
                            }

                            Long affiliationId = voteModel.getAffiliationId();
                            if (affiliationId != null) {
                                affiliation = new Affiliation();
                                affiliation.setAffiliationId(affiliationId);
                                continue;
                            }

                            Long candidateId = voteModel.getCandidateId();
                            if (candidateId != null) {
                                PollingStationVotes pollingStationVotes = new PollingStationVotes(
                                        pollingStation,
                                        affiliation,
                                        candidateService.getCandidateByIdAndAffiliation(candidateId, affiliation),
                                        voteModel.getVotes()
                                );
                                pollingStationVotesService.addPollingStationVotes(pollingStationVotes);
                            }
                            break;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.REPORTING_UNIT_VOTES)) {
                        return;
                    }
            }
        }
    }
}
