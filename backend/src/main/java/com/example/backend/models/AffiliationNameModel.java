package com.example.backend.models;

import com.example.backend.entities.Candidate;
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
