package com.example.backend.XmlParserLearning;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Reader {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        CandidateReader reader = new CandidateReader("res");
        System.out.println("started parsing Candidates");
        reader.parseCandidatesXML();

    }
}
