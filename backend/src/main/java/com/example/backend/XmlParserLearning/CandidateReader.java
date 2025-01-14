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


public class CandidateReader {
    private String path;

     CandidateReader(String path){
         this.path=path;


    }
    public void parseCandidatesXML() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

       List<File> fileList = listFilesFolder(new File(path));
       for (File file : fileList){

           Document document= builder.parse(file);
           document.getDocumentElement().normalize();

           NodeList candidateFullNameList = document.getElementsByTagName("CandidateFullName");
           for (int i = 0 ; i < candidateFullNameList.getLength(); i++){
               Node candidate = candidateFullNameList.item(i);
               if (candidate.getNodeType() == Node.ELEMENT_NODE){
                   NodeList candidateDetails = candidate.getChildNodes();
                   for (int j = 0 ; j < candidateDetails.getLength(); j++){
                       Node fullName = candidateDetails.item(j);
                       if (fullName.getNodeType() == Node.ELEMENT_NODE){
                           Element detailElement = (Element) fullName;
                           System.out.println(detailElement.getTextContent());
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
             System.err.println("the provided path is not valid or is not a directory: "+folder.getAbsolutePath());
         }
         return fileList;
    }
}
