package com.example.backend.xmlreader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class XmlStreamReaderV1 {
    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        XmlStreamReaderV1 xmlStreamReader = new XmlStreamReaderV1();
        xmlStreamReader.parseFile("res/Totaaltelling_TK2021.eml.xml");
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
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    switch (elementName) {
                        case XMLTagNames.CANDIDATE_IDENTIFIER:
                            System.out.println(reader.getAttributeValue(null, XMLTagNames.SHORT_CODE));
                            break;
                        case XMLTagNames.VALID_VOTES:
                            System.out.println(reader.getElementText());
                            break;
                        case XMLTagNames.AFFILIATION_IDENTIFIER:
                            return;
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (reader.getLocalName().equals(XMLTagNames.SELECTION)) {
                        return;
                    }
            }
        }
    }
}
