package com.example.backend.models;

import com.example.backend.DTO.CandidateDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AffiliationVotesModel {
    private String affiliationName;
    private Long affiliationVotes;
    private Long affiliationId;
    private List<CandidateDTO> candidates;

    public AffiliationVotesModel(String affiliationName, Long affiliationVotes, Long affiliationId) {
        this.affiliationName = affiliationName;
        this.affiliationVotes = affiliationVotes;
        this.affiliationId = affiliationId;
    }

}