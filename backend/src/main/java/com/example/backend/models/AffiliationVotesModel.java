package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AffiliationVotesModel {
    public AffiliationVotesModel(String affiliationName, Long affiliationVotes, Long affiliationId) {
        this.affiliationName = affiliationName;
        this.affiliationVotes = affiliationVotes;
        this.AffiliationId = affiliationId;
    }

    private String affiliationName;
    private Long affiliationVotes;
    private Long AffiliationId;
}
