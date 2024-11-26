package com.example.backend.models;

import com.example.backend.entities.ConstituencyVotes;
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

    public ConstituencyVotesModel(ConstituencyVotes constituencyVotes) {
        this.constituencyName = constituencyVotes.getConstituency().getConstituencyName();
        this.affiliationName = constituencyVotes.getAffiliation().getAffiliationName();
        this.affiliationId = constituencyVotes.getAffiliation().getAffiliationId();
        this.candidateName = constituencyVotes.getCandidate().getFullName();
        this.constituencyVotes = constituencyVotes.getTotalVotes();
        this.constituencyId = constituencyVotes.getConstituency().getConstituencyId();
        this.candidateId = constituencyVotes.getCandidate().getCandidateId();
    }
}
