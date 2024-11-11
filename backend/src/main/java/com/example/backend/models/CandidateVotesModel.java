package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CandidateVotesModel {
    public CandidateVotesModel(long candidateId, String initials, String lastnamePrefix, String lastname, Long votes) {
        this.candidateId = candidateId;
        this.name = getFullName(initials, lastnamePrefix, lastname);
        this.votes = votes;
    }

    private long candidateId;
    private String name;
    private long votes;

    public String getFullName(String initials, String lastnamePrefix, String lastname) {
        return (initials != null ? initials + " " : "") +
                (lastnamePrefix != null ? lastnamePrefix + " " : "") +
                (lastname != null ? lastname : "");
    }
}