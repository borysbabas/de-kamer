package com.example.backend.models;

import com.example.backend.xmlreader.XMLTagNames;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionModel {
    private Long regionNumber;
    private String regionName;
    private String regionCategory;
    private Long superiorRegionNumber;
    private String superiorRegionCategory;
    private XMLTagNames.COMMITTEE_TYPE committeeType;
}
