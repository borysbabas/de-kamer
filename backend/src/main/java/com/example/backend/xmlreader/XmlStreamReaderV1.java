package com.example.backend.xmlreader;

import com.example.backend.models.VoteModel;
import com.example.backend.services.*;
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
    private final AffiliationService affiliationService;
    private final AffiliationVotesService affiliationVotesService;
    private final ConstituencyService constituencyService;
    private final ConstituencyVotesService constituencyVotesService;
    private final MunicipalityService municipalityService;
    private final MunicipalityVotesService municipalityVotesService;
    private XMLTagNames.FILE_TYPE fileType;

    public XmlStreamReaderV1(ApplicationContext applicationContext) {
        this.candidateService = applicationContext.getBean(CandidateService.class);
        this.countryVotesService = applicationContext.getBean(CountryVotesService.class);
        this.affiliationService = applicationContext.getBean(AffiliationService.class);
        this.affiliationVotesService = applicationContext.getBean(AffiliationVotesService.class);
        this.constituencyService = applicationContext.getBean(ConstituencyService.class);
        this.municipalityService = applicationContext.getBean(MunicipalityService.class);
        this.constituencyVotesService = applicationContext.getBean(ConstituencyVotesService.class);
        this.municipalityVotesService = applicationContext.getBean(MunicipalityVotesService.class);
    }

    public void parseFile(String filename, XMLTagNames.FILE_TYPE fileType) throws FileNotFoundException, XMLStreamException {
        InputStream raw = new FileInputStream(filename);
        this.fileType = fileType;
        readFromXml(raw);
        System.out.printf("Read file: %s%n", filename);
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
                switch (fileType) {
                    case TOTAL_COUNT:
                        new XmlTotalCountReader(
                                affiliationService,
                                affiliationVotesService,
                                candidateService,
                                countryVotesService
                        ).read(reader);
                        break;
                    case CANDIDATE_LIST:
                        new XmlCandidateListReader(
                                affiliationService,
                                candidateService,
                                constituencyService
                        ).read(reader);
                        break;
                    case ELECTION_TREE:
                        new XmlElectionTreeReader(
                                constituencyService,
                                municipalityService
                        ).read(reader);
                        break;
                    case VOTES_CONSTITUENCY:
                        new XmlConstituencyVotesReader(
                                candidateService,
                                constituencyService,
                                constituencyVotesService
                        ).read(reader);
                        break;
                    case VOTES_MUNICIPALITY:
                        new XmlMunicipalityVotesReader(
                                constituencyService,
                                municipalityVotesService,
                                candidateService
                        ).read(reader);
                }
            }
        }
    }

    public static VoteModel readSelection(XMLStreamReader reader) throws XMLStreamException {
        VoteModel voteModel = new VoteModel();
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case XMLTagNames.AFFILIATION_ID:
                            voteModel.setAffiliationId(Long.parseLong(reader.getAttributeValue(0)));
                            return voteModel;
                        case XMLTagNames.CANDIDATE_ID:
                            Long candidateId = Long.parseLong(reader.getAttributeValue(0));
                            voteModel.setCandidateId(candidateId);
                            break;
                        case XMLTagNames.VALID_VOTES:
                            Long votes = Long.parseLong(reader.getElementText());
                            voteModel.setVotes(votes);
                            break;
                    }
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.SELECTION)) {
                        return voteModel;
                    }
                    break;
            }
        }
        return null;
    }
}
