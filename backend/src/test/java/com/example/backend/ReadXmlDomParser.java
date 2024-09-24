package com.example.backend;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;

public class ReadXmlDomParser {

    private static final String FILENAME = "backend/src/test/resources/test2.xml";
    private static final String OUTPUT_FILENAME = "backend/src/test/resources/output.txt";

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            System.out.println("Root element: " + root.getNodeName());

            NodeList nodeList = doc.getElementsByTagName("Candidate");

            int extraId = 0;

            FileWriter writer = new FileWriter(OUTPUT_FILENAME);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    Node idNode = element.getElementsByTagName("CandidateIdentifier").item(0);
                    Node initialsNode = element.getElementsByTagName("xnl:NameLine").item(0);
                    Node firstNameNode = element.getElementsByTagName("xnl:FirstName").item(0);
                    Node lastNameNode = element.getElementsByTagName("xnl:LastName").item(0);
                    Node genderNode = element.getElementsByTagName("Gender").item(0);
                    Node localityNode = element.getElementsByTagName("xal:LocalityName").item(0);

                    if (idNode != null && initialsNode != null && firstNameNode != null && lastNameNode != null && genderNode != null && localityNode != null) {
                        String id = idNode.getAttributes().getNamedItem("Id").getTextContent();
                        String initials = initialsNode.getTextContent();
                        String firstName = firstNameNode.getTextContent();
                        String lastName = lastNameNode.getTextContent();
                        String gender = genderNode.getTextContent();
                        String locality = localityNode.getTextContent();


                        System.out.println("Extra ID: " + extraId);
                        System.out.println("Candidate ID: " + id);
                        System.out.println("Initials: " + initials);
                        System.out.println("First Name: " + firstName);
                        System.out.println("Last Name: " + lastName);
                        System.out.println("Gender: " + gender);
                        System.out.println("Locality: " + locality);
                        System.out.println();

                        writer.write("Extra ID: " + extraId + "\n");
                        writer.write("Candidate ID: " + id + "\n");
                        writer.write("Initials: " + initials + "\n");
                        writer.write("First Name: " + firstName + "\n");
                        writer.write("Last Name: " + lastName + "\n");
                        writer.write("Gender: " + gender + "\n");
                        writer.write("Locality: " + locality + "\n");
                        writer.write("\n");

                        extraId++;
                    }
                }
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}