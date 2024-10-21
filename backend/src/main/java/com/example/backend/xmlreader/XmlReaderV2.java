package com.example.backend.xmlreader;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import com.example.backend.services.CandidateService;
import com.example.backend.services.AffiliationService;
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
    // services
    private final AffiliationService affiliationService;
    private final CandidateService candidateService;

    public XmlReaderV2(ApplicationContext applicationContext) {
        System.out.println(XMLTagNames.AFFILIATION);
        this.affiliationService = applicationContext.getBean(AffiliationService.class);
        this.candidateService = applicationContext.getBean(CandidateService.class);
    }

    public void parseAndInsert(String filename) {
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            System.out.println("Root element: " + root.getNodeName());

            // Get the list of affiliations
            NodeList affiliationList = doc.getElementsByTagName(XMLTagNames.AFFILIATION);

            // Loop through the list of affiliations
            for (int i = 0; i < affiliationList.getLength(); i++) {
                System.out.println("Affiliation: " + i);
                Node affiliationNode = affiliationList.item(i);

                if (affiliationNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element affiliaitionElement = (Element) affiliationNode;

                    String affiliationIdentifier = affiliaitionElement.getElementsByTagName(XMLTagNames.AFFILIATION_IDENTIFIER).item(0).getTextContent().trim();
                    Affiliation affiliation = new Affiliation((long) i, affiliationIdentifier);

                    affiliationService.addAffiliation(affiliation);

                    System.out.println("Affiliation Identifier: " + affiliationIdentifier);

                    NodeList candidateList = affiliaitionElement.getElementsByTagName(XMLTagNames.CANDIDATE);
                    System.out.println("Candidate List Length: " + candidateList.getLength());

                    // Loop through the list of candidates
                    for (int j = 0; j < candidateList.getLength(); j++) {
                        Candidate candidate = new Candidate();
                        candidate.setAffiliation(affiliation);
                        Node candidateNode = candidateList.item(j);
                        String namePrefix = null;
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
                            Node candidateAffiliationNode = candidateElement.getElementsByTagName(XMLTagNames.CANDIDATE_IDENTIFIER).item(0);

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
                                    namePrefix = namePrefixNode.getTextContent();
                                    candidate.setLastNamePrefix(namePrefix);
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

                                String memberId = candidateAffiliationNode.getAttributes().getNamedItem("Id").getNodeValue();
                                candidate.setMemberId(Long.parseLong(memberId));
//                                candidate.setMemberId((long) j);



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        candidateService.addCandidate(candidate, affiliation);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
