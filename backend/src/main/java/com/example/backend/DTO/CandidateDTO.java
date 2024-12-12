package com.example.backend.DTO;

import com.example.backend.entities.Candidate;
import com.example.backend.entities.CountryVotes;
import com.example.backend.entities.MunicipalityVotes;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class CandidateDTO {
    private  Long candidateId;
    private String firstName;
    private String prefix;
    private String lastName;
    private String affiliationName;
    private String gender;
    private Long votes;

    public CandidateDTO(Candidate candidate) {

        this.candidateId = candidate.getCandidateId();
        this.firstName = candidate.getFirstName();
        this.prefix = candidate.getLastNamePrefix();
        this.lastName = candidate.getLastName();
        this.affiliationName = candidate.getAffiliation().getAffiliationName();
        this.gender=candidate.getGender();

    }

    public CandidateDTO(CountryVotes countryVotes) {
        Candidate candidate = countryVotes.getCandidate();
        this.candidateId = candidate.getCandidateId();
        this.firstName = candidate.getFirstName();
        this.prefix = candidate.getLastNamePrefix();
        this.lastName = candidate.getLastName();
        this.affiliationName = candidate.getAffiliation().getAffiliationName();
        this.votes = countryVotes.getTotalVotes();
    }

    public CandidateDTO() {
        // Default constructor
    }
}