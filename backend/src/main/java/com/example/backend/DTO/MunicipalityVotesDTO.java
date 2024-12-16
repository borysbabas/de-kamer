package com.example.backend.DTO;

import com.example.backend.entities.MunicipalityVotes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipalityVotesDTO {
    private String municipalityName;
    private String affiliationName;
    private String candidateName;
    private Long totalVotes;
    private Long municipalityId;
    private Long affiliationId;
    private Long candidateId;

    public MunicipalityVotesDTO(MunicipalityVotes municipalityVotes) {
        this.municipalityName = municipalityVotes.getMunicipality().getMunicipalityName();
        this.affiliationName = municipalityVotes.getAffiliation().getAffiliationName();
        this.candidateName = municipalityVotes.getCandidate().getFullName();
        this.totalVotes = municipalityVotes.getTotalVotes();
        this.municipalityId = municipalityVotes.getMunicipality().getMunicipalityId();
        this.affiliationId = municipalityVotes.getAffiliation().getAffiliationId();
        this.candidateId = municipalityVotes.getCandidate().getCandidateId();
    }
}
