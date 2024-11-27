package com.example.backend.models;

import com.example.backend.entities.Municipality;
import com.example.backend.entities.MunicipalityVotes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipalityVotesModel {
    private long municipalityId;
    private long affiliationId;
    private String affiliationName;
    private long candidateId;
    private String name;
    private long constituencyId;
    private long totalVotes;

    public MunicipalityVotesModel(MunicipalityVotes municipalityVotes) {
        this.municipalityId = municipalityVotes.getMunicipality().getMunicipalityId();
        this.name = municipalityVotes.getMunicipality().getMunicipalityName();
        this.constituencyId = municipalityVotes.getConstituency().getConstituencyId();
        this.totalVotes = municipalityVotes.getTotalVotes();
        this.candidateId = municipalityVotes.getCandidate().getCandidateId();
        this.affiliationId = municipalityVotes.getAffiliation().getAffiliationId();
        this.affiliationName = municipalityVotes.getAffiliation().getAffiliationName();
    }
}
