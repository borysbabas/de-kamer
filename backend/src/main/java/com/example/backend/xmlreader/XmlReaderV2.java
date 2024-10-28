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
//        System.out.println(XMLTagNames.AFFILIATION);
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

//            System.out.println("Root element: " + root.getNodeName());

            Long constituencyId = Long.valueOf(doc.getElementsByTagName(XMLTagNames.CONTEST_ID).item(0).getAttributes().getNamedItem(XMLTagNames.ID).getNodeValue());

            // Get the list of affiliations
            NodeList affiliationList = doc.getElementsByTagName(XMLTagNames.AFFILIATION);

            // Loop through the list of affiliations
            for (int i = 0; i < affiliationList.getLength(); i++) {
//                System.out.println("Affiliation: " + i);
                Node affiliationNode = affiliationList.item(i);

                if (affiliationNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element affiliationElement = (Element) affiliationNode;

                    Node affiliationIdentifierNode = affiliationElement.getElementsByTagName(XMLTagNames.AFFILIATION_IDENTIFIER).item(0);
                    String affiliationName = affiliationIdentifierNode.getTextContent().trim();
                    Long affiliationIdentifier = Long.parseLong(affiliationIdentifierNode.getAttributes().getNamedItem(XMLTagNames.ID).getNodeValue());
                    String affiliationType = affiliationElement.getElementsByTagName(XMLTagNames.TYPE).item(0).getTextContent().trim();

                    boolean identicalLists = affiliationType.equals(XMLTagNames.IDENTICAL_LISTS);

                    Affiliation affiliation = affiliationService.findAffiliationByAffiliationId(affiliationIdentifier);

                    if (affiliation == null) {
                        affiliation = new Affiliation(affiliationIdentifier, affiliationName);
                        affiliation.setIdenticalLists(identicalLists);
                    } else if (identicalLists) {
                        continue;
                    }

                    affiliationService.addAffiliation(affiliation);

//                    System.out.println("Affiliation Identifier: " + affiliationIdentifier);

                    NodeList candidateList = affiliationElement.getElementsByTagName(XMLTagNames.CANDIDATE);
//                    System.out.println("Candidate List Length: " + candidateList.getLength());

                    // Loop through the list of candidates
                    for (int j = 0; j < candidateList.getLength(); j++) {
                        Candidate candidate = new Candidate();
                        candidate.setAffiliation(affiliation);
                        Node candidateNode = candidateList.item(j);
                        String namePrefix;
                        String gender;
                        String firstName;

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
                                    // do nothing
                                }
                                try {
                                    namePrefix = namePrefixNode.getTextContent();
                                    candidate.setLastNamePrefix(namePrefix);
                                } catch (Exception e) {
                                    // do nothing
                                }
                                String lastName = lastNameNode.getTextContent();
                                candidate.setLastName(lastName);

                                try {
                                    gender = genderNode.getTextContent();
                                    candidate.setGender(gender);
                                } catch (Exception e) {
                                    // do nothing
                                }
                                String locality = localityNode.getTextContent();
                                candidate.setLocality(locality);

                                String memberId = candidateAffiliationNode.getAttributes().getNamedItem("Id").getNodeValue();
                                candidate.setMemberId(Long.parseLong(memberId));

                                if (identicalLists) {
                                    // Use 0 for identical lists
                                    candidate.setConstituencyId(0L);
                                } else {
                                    candidate.setConstituencyId(constituencyId);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        candidate.setShortCode(candidateService.toShortCode(candidate));
                        candidateService.addCandidate(candidate, affiliation);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
