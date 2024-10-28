package com.example.backend.xmlreader;

import com.example.backend.services.AffiliationService;
import com.example.backend.services.CandidateService;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Objects;

public class XmlCandidateListReader {
    private final AffiliationService affiliationService;
    private final CandidateService candidateService;


    public XmlCandidateListReader(AffiliationService affiliationService, CandidateService candidateService) {
        this.affiliationService = affiliationService;
        this.candidateService = candidateService;
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
                if (elementName.equals(XMLTagNames.AFFILIATION)) {
                    readAffiliation(reader);
                }
            }
        }
    }

    private void readAffiliation(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals(XMLTagNames.AFFILIATION_IDENTIFIER)) {
                    Long affiliationId = Long.parseLong(reader.getAttributeValue(null, XMLTagNames.ID));
                    if (affiliationService.findAffiliationNameByAffiliationId(affiliationId) != null) {
                        if (Objects.equals(readAffiliationType(reader), XMLTagNames.IDENTICAL_LISTS)) return;
                    }

                }
            }
        }
    }

    private String readAffiliationType(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals(XMLTagNames.TYPE)) {
                    return reader.getAttributeValue(null, XMLTagNames.TYPE);
                }
            }
        }
        return null;
    }
}
