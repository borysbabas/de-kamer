package com.example.backend.models;

import com.example.backend.xmlreader.XMLTagNames;

public record XmlFileModel(String filePath, XMLTagNames.FILE_TYPE fileType) {
}
