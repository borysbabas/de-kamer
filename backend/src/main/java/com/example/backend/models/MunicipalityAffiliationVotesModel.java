package com.example.backend.models;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Municipality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipalityAffiliationVotesModel {
    private String municipalityName;
    private Long municipalityId;
    private String affiliationName;
    private Long affiliationId;
    private Long totalVotes;

    public MunicipalityAffiliationVotesModel(Municipality municipality, Affiliation affiliation, Long totalVotes, Long municipalityId){
        this.municipalityName = municipality.getMunicipalityName();
        this.affiliationName = affiliation.getAffiliationName();
        this.affiliationId = affiliation.getAffiliationId();
        this.municipalityId = municipalityId;
        this.totalVotes = totalVotes;
    }
}
