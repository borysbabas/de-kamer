package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstituencyVotesModel {
    private Long constituencyId;
    private String constituencyName;
    private Long totalVotes;

    public ConstituencyVotesModel() {
    }

    public ConstituencyVotesModel(String constituencyName, Long totalVotes) {
        this.constituencyName = constituencyName;
        this.totalVotes = totalVotes;
    }
}
