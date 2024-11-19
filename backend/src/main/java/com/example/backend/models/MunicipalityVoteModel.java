package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipalityVoteModel {
    private String municipalityName;
    private Long totalVotes;

    public MunicipalityVoteModel(String municipalityName, Long totalVotes){
        this.municipalityName=municipalityName;
        this.totalVotes=totalVotes;
    }
}
