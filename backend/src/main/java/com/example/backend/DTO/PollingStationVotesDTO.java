package com.example.backend.DTO;

import com.example.backend.entities.PollingStationVotes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PollingStationVotesDTO {
    private String pollingStationName;
    private String affiliationName;
    private String candidateName;
    private Long totalVotes;
    private Long pollingStationId;
    private Long affiliationId;
    private Long candidateId;

    public PollingStationVotesDTO(PollingStationVotes pollingStationVotes) {
        this.pollingStationName = pollingStationVotes.getPollingStation().getPollingStationName();
        this.affiliationName = pollingStationVotes.getAffiliation().getAffiliationName();
        this.candidateName = pollingStationVotes.getCandidate().getFullName();
        this.totalVotes = pollingStationVotes.getTotalVotes();
        this.pollingStationId = pollingStationVotes.getId();
        this.affiliationId = pollingStationVotes.getAffiliation().getAffiliationId();
        this.candidateId = pollingStationVotes.getCandidate().getCandidateId();
    }
}
