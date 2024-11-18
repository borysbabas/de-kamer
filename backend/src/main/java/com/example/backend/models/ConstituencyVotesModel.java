package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstituencyVotesModel {
    private String constituencyName;
    private String affiliationName;
    private String candidateName;
    private Long constituencyVotes;
    private Long constituencyId;
    private Long affiliationId;
    private Long candidateId;

    public ConstituencyVotesModel(String constituencyName, String affiliationName, String candidateName, Long constituencyVotes, Long constituencyId, Long affiliationId, Long candidateId) {
        this.constituencyName = constituencyName;
        this.affiliationName = affiliationName;
        this.candidateName = candidateName;
        this.constituencyVotes = constituencyVotes;
        this.constituencyId = constituencyId;
        this.affiliationId = affiliationId;
        this.candidateId = candidateId;
    }
}
