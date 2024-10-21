package com.example.backend.xmlreader;

import com.example.backend.entities.Candidate;
import com.example.backend.entities.Party;
import com.example.backend.services.CandidateService;
import com.example.backend.services.PartyService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class XmlReaderV2 {
    private static final String FILENAME = "res/Kandidatenlijsten_TK2021_Amsterdam.eml.xml";

    // services
    private final PartyService partyService;
    private final CandidateService candidateService;

    public XmlReaderV2(ApplicationContext applicationContext) {
        this.partyService = applicationContext.getBean(PartyService.class);
        this.candidateService = applicationContext.getBean(CandidateService.class);
    }

    public void parseAndInsert() {
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            System.out.println("Root element: " + root.getNodeName());

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

                    partyService.addParty(party);

                    System.out.println("Affiliation Identifier: " + affiliationIdentifier);

                    NodeList candidateList = affiliaitionElement.getElementsByTagName("Candidate");
                    System.out.println("Candidate List Length: " + candidateList.getLength());

                    // Loop through the list of candidates
                    for (int j = 0; j < candidateList.getLength(); j++) {
                        Candidate candidate = new Candidate();
                        candidate.setParty(party);
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
                                candidate.setInitials(initials);
                                try {
                                    firstName = firstNameNode.getTextContent();
                                    candidate.setFirstName(firstName);
                                } catch (Exception e) {
                                    firstName = "Not Specified";
                                }
                                try {
                                    NamePrefix = namePrefixNode.getTextContent();
                                } catch (Exception e) {
                                    //do nothing
                                }
                                String lastName = lastNameNode.getTextContent();
                                candidate.setLastName(lastName);
                                try {
                                    gender = genderNode.getTextContent();
                                    candidate.setGender(gender);
                                } catch (Exception e) {
                                    gender = "Not Specified";
                                }
                                String locality = localityNode.getTextContent();
                                candidate.setLocality(locality);
                                candidate.setMemberId((long) j);



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        candidateService.addCandidate(candidate);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
