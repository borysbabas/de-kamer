package com.example.backend.xmlreader;

import com.example.backend.entities.Constituency;
import com.example.backend.models.RegionModel;
import com.example.backend.services.ConstituencyService;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static com.example.backend.xmlreader.XMLTagNames.COMMITTEE_TYPE.HSB;
import static com.example.backend.xmlreader.XMLTagNames.COMMITTEE_TYPE.PSB;

public class XmlElectionTreeReader {
    private final ConstituencyService constituencyService;
    public XmlElectionTreeReader(ConstituencyService constituencyService) {
        this.constituencyService = constituencyService;
    }

    public void read(XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals(XMLTagNames.ELECTION_TREE)) {
                    readElectionTree(reader);
                }
            }
        }
    }

    private void readElectionTree(XMLStreamReader reader) throws XMLStreamException {
        RegionModel region = new RegionModel();
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    switch (elementName) {
                        case XMLTagNames.REGION:
                            region = new RegionModel();
                            String regionNumber = reader.getAttributeValue(null, XMLTagNames.REGION_NUMBER);
                            String superiorRegionNumber = reader.getAttributeValue(null, XMLTagNames.SUPERIOR_REGION_NUMBER);

                            String regionCategory = reader.getAttributeValue(null, XMLTagNames.REGION_CATEGORY);
                            String superiorRegionCategory = reader.getAttributeValue(null, XMLTagNames.SUPERIOR_REGION_CATEGORY);

                            if (regionNumber != null) {
                                region.setRegionNumber(Long.valueOf(regionNumber));
                            }
                            if (superiorRegionNumber != null) {
                                region.setSuperiorRegionNumber(Long.valueOf(superiorRegionNumber));
                            }
                            region.setRegionCategory(regionCategory);
                            region.setSuperiorRegionCategory(superiorRegionCategory);
                            break;
                        case XMLTagNames.REGION_NAME:
                            region.setRegionName(reader.getElementText());
                            break;
                        case XMLTagNames.COMMITTEE:
                            XMLTagNames.COMMITTEE_TYPE committeeName = XMLTagNames.COMMITTEE_TYPE.valueOf(reader.getAttributeValue(0));
                            region.setCommitteeType(committeeName);
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    String name = reader.getLocalName();
                    if (name.equals(XMLTagNames.REGION)) {
                        switch(region.getCommitteeType()) {
                            case HSB:
                                constituencyService.addConstituency(new Constituency(
                                        region.getRegionNumber(),
                                        region.getRegionName()
                                ));
                                break;
                            case PSB:
                                break;
                        }
                    }
                    break;
            }
        }
    }
}
