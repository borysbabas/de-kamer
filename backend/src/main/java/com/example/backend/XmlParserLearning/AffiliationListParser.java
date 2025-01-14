package com.example.backend.XmlParserLearning;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AffiliationListParser {

    private String path;

    AffiliationListParser(String path){
        this.path=path;

    }
    public void readAffiliationList() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        List<File> fileList = listFilesFolder(new File(path));
        for (File file : fileList){

            Document document= builder.parse(file);
            document.getDocumentElement().normalize();

            NodeList candidateElementList = document.getElementsByTagName("Selection");

            for (int i = 0 ; i < candidateElementList.getLength(); i++){
                Node selection = candidateElementList.item(i);
                if (selection.getNodeType() == Node.ELEMENT_NODE){
                    NodeList selectionDetails = selection.getChildNodes();
                    for (int j = 0 ; j < selectionDetails.getLength(); j++){
                        Node validVotes = selectionDetails.item(j);
                        if (validVotes.getNodeType() == Node.ELEMENT_NODE){
                            Element detailElement = (Element) validVotes;
                            if ("ValidVotes".equals(detailElement.getTagName()))
                             System.out.println("Valid Votes: "+ detailElement.getTextContent());
                        }
                    }


                }
            }
        }

    }
    private List<File> listFilesFolder(final File folder){
        List<File>fileList = new ArrayList<>();
        if (folder.exists()&& folder.isDirectory()){
            for (final File fileEntry : folder.listFiles()){
                if (fileEntry.isDirectory()){

                    fileList.addAll(listFilesFolder(fileEntry));
                }else if(fileEntry.getName().toLowerCase().endsWith(".xml")){
                    fileList.add(fileEntry);
                }
            }

        }else{
            throw new IllegalArgumentException("the provided path is not valid or is not a diretory");
        }
        return fileList;
    }
}
