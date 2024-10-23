package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AffiliationVotes {
    public AffiliationVotes(String affiliationName, Long affiliationVotes) {
        this.affiliationName = affiliationName;
        this.affiliationVotes = affiliationVotes;
    }

    private String affiliationName;
    private Long affiliationVotes;
}
