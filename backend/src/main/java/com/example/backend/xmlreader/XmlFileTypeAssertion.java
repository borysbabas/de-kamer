package com.example.backend.xmlreader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlFileTypeAssertion {
    public XMLTagNames.FILE_TYPE assertFile(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLStreamReader.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case XMLTagNames.ELECTION_EVENT:
                            return XMLTagNames.FILE_TYPE.ELECTION_TREE;
                        case XMLTagNames.CANDIDATE_LIST:
                            return XMLTagNames.FILE_TYPE.CANDIDATE_LIST;
                        case XMLTagNames.RESULT:
                            return XMLTagNames.FILE_TYPE.RESULT;
                        case XMLTagNames.CONTEST_ID:
                            if (reader.getAttributeValue(null, XMLTagNames.ID).equals(XMLTagNames.ALL_D)) {
                                return XMLTagNames.FILE_TYPE.TOTAL_COUNT;
                            }
                        case XMLTagNames.AUTHORITY_ID:
                            String authorityId = reader.getAttributeValue(null, XMLTagNames.ID);
                            try {
                                long municipalityId = Long.parseLong(authorityId);
                                if (municipalityId >= 0) {
                                    return XMLTagNames.FILE_TYPE.VOTES_MUNICIPALITY;
                                }
                            } catch (NumberFormatException e) {
                                if (authorityId.contains(XMLTagNames.HSB)) return XMLTagNames.FILE_TYPE.VOTES_CONSTITUENCY;
                            }
                    }
                    break;
            }
        }
        return null;
    }
}
