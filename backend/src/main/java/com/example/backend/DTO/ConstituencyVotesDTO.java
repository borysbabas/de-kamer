package com.example.backend.DTO;

import com.example.backend.entities.ConstituencyVotes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstituencyVotesDTO {
    private String constituencyName;
    private String affiliationName;
    private String candidateName;
    private Long totalVotes;
    private Long constituencyId;
    private Long affiliationId;
    private Long candidateId;

    public ConstituencyVotesDTO(ConstituencyVotes constituencyVotes) {
        this.constituencyName = constituencyVotes.getConstituency().getConstituencyName();
        this.affiliationName = constituencyVotes.getAffiliation().getAffiliationName();
        this.affiliationId = constituencyVotes.getAffiliation().getAffiliationId();
        this.candidateName = constituencyVotes.getCandidate().getFullName();
        this.totalVotes = constituencyVotes.getTotalVotes();
        this.constituencyId = constituencyVotes.getConstituency().getConstituencyId();
        this.candidateId = constituencyVotes.getCandidate().getCandidateId();
    }
}
