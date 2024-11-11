package com.example.backend.xmlreader;

import com.example.backend.models.XmlFileModel;
import lombok.Getter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class FileDiscovery {
    private static final int maxPriority = 4;
    @Getter
    private static final ArrayList<XmlFileModel>[] files = new ArrayList[maxPriority+1];

    /**
     * Discover all the files from the given path
     *
     * @param path path to the directory to search from project root
     * @return Array with Array of files
     */
    public static ArrayList<XmlFileModel>[] discoverFiles(String path) throws XMLStreamException, FileNotFoundException {
        setFiles();
        readDirectory("./" + path);
        return files;
    }

    private static void setFiles() {
        for (int priority = 0; priority < maxPriority; priority++) {
            files[priority] = new ArrayList<>();
        }
    }

    private static void readDirectory(String path) throws XMLStreamException, FileNotFoundException {
        File dir = new File(path);
        File[] dirFiles = dir.listFiles();
        if (dirFiles == null) {
            System.err.printf("No files found in directory \"%s\", please enter a valid directory%n", dir.getAbsolutePath());
            throw new FileNotFoundException("No files found");
        }
        for (int i = 0; i < Objects.requireNonNull(dirFiles).length; i++) {
            File file = dirFiles[i];
            if (file.isFile() && file.getName().endsWith(".xml")) {
                String filePath = file.getPath();
                XMLTagNames.FILE_TYPE fileType = getFileType(filePath);
                XmlFileModel fileModel = new XmlFileModel(
                        filePath,
                        fileType
                );

                int fileTypePriority = getFileTypePriority(fileType);
                files[fileTypePriority].add(fileModel);
            } else if (file.isDirectory()) {
                readDirectory(file.getPath());
            }
        }
    }

    private static XMLTagNames.FILE_TYPE getFileType(String filePath) throws FileNotFoundException, XMLStreamException {
        InputStream raw = new FileInputStream(filePath);
        return parseFileType(raw);
    }

    private static XMLTagNames.FILE_TYPE parseFileType(InputStream inputStream) throws XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        XmlFileTypeAssertion xmlFileTypeAssertion = new XmlFileTypeAssertion();

        try {
            reader = inputFactory.createXMLStreamReader(inputStream);
            return xmlFileTypeAssertion.assertFile(reader);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static int getFileTypePriority(XMLTagNames.FILE_TYPE fileType) {
        return switch (fileType) {
            case ELECTION_TREE -> 0;
            case CANDIDATE_LIST -> 1;
            case RESULT, TOTAL_COUNT, VOTES_CONSTITUENCY -> 2;
            case VOTES_MUNICIPALITY -> 3;
        };
    }
}
