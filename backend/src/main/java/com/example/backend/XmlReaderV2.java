package com.example.backend;

import com.example.backend.entities.Party;
import com.example.backend.services.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;

@Component
public class XmlReaderV2 {
        private static final String FILENAME = "res/Kandidatenlijsten_TK2021_Amsterdam.eml.xml";
        private static final String OUTPUT_FILENAME = "backend/src/test/resources/output.txt";

        private static PartyService partyService;

        @Autowired
        public XmlReaderV2(PartyService partyService) {
            this.partyService = partyService;
        }

        public static void main(String[] args) {
            try {

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new File(FILENAME));

                doc.getDocumentElement().normalize();

                Element root = doc.getDocumentElement();

                System.out.println("Root element: " + root.getNodeName());

                // Prepare the output file
                int localId = 0;
                FileWriter writer = new FileWriter(OUTPUT_FILENAME);

                // Get the list of affiliations
                NodeList affiliationList = doc.getElementsByTagName("Affiliation");

                // Loop through the list of affiliations
                for (int i = 0; i < affiliationList.getLength(); i++) {
                    System.out.println("Affiliation: " + i);
                    Node affiliationNode = affiliationList.item(i);

                    if (affiliationNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element affiliaitionElement = (Element) affiliationNode;

                        String affiliationIdentifier = affiliaitionElement.getElementsByTagName("AffiliationIdentifier").item(0).getTextContent().trim();
                        Party party = new Party((long) i, affiliationIdentifier);



                        writer.write("Affiliation Identifier: " + affiliationIdentifier + "\n");
                        System.out.println("Affiliation Identifier: " + affiliationIdentifier);

                        NodeList candidateList = affiliaitionElement.getElementsByTagName("Candidate");
                        System.out.println("Candidate List Length: " + candidateList.getLength());
                        writer.write("Candidate List Length: " + candidateList.getLength() + "\n");

                        // Loop through the list of candidates
                        for (int j = 0; j < candidateList.getLength(); j++) {
                            Node candidateNode = candidateList.item(j);
                            String NamePrefix = null;
                            String gender = null;
                            String firstName = null;

                            if (candidateNode != null && candidateNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element candidateElement = (Element) candidateNode;

                                Node initialsNode = candidateElement.getElementsByTagName("xnl:NameLine").item(0);
                                Node firstNameNode = candidateElement.getElementsByTagName("xnl:FirstName").item(0);
                                Node namePrefixNode = candidateElement.getElementsByTagName("xnl:NamePrefix").item(0);
                                Node lastNameNode = candidateElement.getElementsByTagName("xnl:LastName").item(0);
                                Node genderNode = candidateElement.getElementsByTagName("Gender").item(0);
                                Node localityNode = candidateElement.getElementsByTagName("xal:LocalityName").item(0);

                                try {
                                    String initials = initialsNode.getTextContent();
                                    try {
                                        firstName = firstNameNode.getTextContent();
                                    } catch (Exception e) {
                                        firstName = "Not Specified";
                                    }
                                    try {
                                        NamePrefix = namePrefixNode.getTextContent();
                                    } catch (Exception e) {
                                        //do nothing
                                    }
                                    String lastName = lastNameNode.getTextContent();
                                    try {
                                        gender = genderNode.getTextContent();
                                    } catch (Exception e) {
                                        gender = "Not Specified";
                                    }
                                    String locality = localityNode.getTextContent();

                                    writer.write("Affiliation Identifier: " + affiliationIdentifier + "\n");
                                    writer.write("\n");
                                    writer.write("Candidate: " + j + "\n");
                                    writer.write("Initials: " + initials + "\n");
                                    writer.write("First Name: " + firstName + "\n");
                                    if (NamePrefix != null) {
                                        writer.write("Last Name: " + NamePrefix + " " + lastName + "\n");
                                    } else {
                                        writer.write("Last Name: " + lastName + "\n");
                                    }
                                    writer.write("Gender: " + gender + "\n");
                                    writer.write("Locality: " + locality + "\n");
                                    writer.write("\n");

                                    partyService.addParty(party);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


}
