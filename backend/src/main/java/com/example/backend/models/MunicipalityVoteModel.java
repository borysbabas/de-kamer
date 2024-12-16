package com.example.backend.models;

import com.example.backend.entities.Municipality;
import com.example.backend.entities.MunicipalityVotes;
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

    public MunicipalityVoteModel(Municipality municipality, Long totalVotes) {
        this.municipalityName = municipality.getMunicipalityName();
        this.totalVotes = totalVotes;
    }
}
