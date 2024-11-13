package com.example.backend.models;

import com.example.backend.entities.Candidate;
import com.example.backend.entities.CountryVotes;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CandidateVotesModel {
    private long candidateId;
    private String name;
    private String lastName;
    private String firstName;
    private String prefix;
    private String affiliationName;
    private String gender;
    private long votes;

    public CandidateVotesModel(CountryVotes countryVotes) {
        Candidate candidate = countryVotes.getCandidate();
        this.candidateId = candidate.getCandidateId();
        this.votes = countryVotes.getTotalVotes();
        this.firstName = candidate.getFirstName();
        this.prefix = candidate.getLastNamePrefix();
        this.lastName = candidate.getLastName();
        this.affiliationName = countryVotes.getAffiliation().getAffiliationName();
        this.name = getFullName(candidate.getInitials(), this.prefix, this.lastName);
        this.gender=candidate.getGender();
    }

    public String getFullName(String initials, String lastnamePrefix, String lastname) {
        return (initials != null ? initials + " " : "") +
                (lastnamePrefix != null ? lastnamePrefix + " " : "") +
                (lastname != null ? lastname : "");
    }
}