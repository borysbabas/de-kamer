package com.example.backend.models;

import lombok.Getter;

@Getter

public class AffiliationNameModel {

    private final String affiliationName;
    private final String firstCandidateName;

    public AffiliationNameModel(String affiliationName, String firstCandidateName) {
        this.affiliationName = affiliationName;
        this.firstCandidateName = firstCandidateName;
    }

}
