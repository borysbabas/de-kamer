package com.example.backend.DTO;

import com.example.backend.entities.Candidate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class CandidateDTO {
    private  Long candidateId;
    private String firstName;
    private String prefix;
    private String lastName;
    private String affiliationName;

    public CandidateDTO(Candidate candidate){
        this.candidateId=candidate.getCandidateId();
        this.firstName=candidate.getFirstName();
        this.prefix=candidate.getLastNamePrefix();
        this.lastName=candidate.getLastName();
        this.affiliationName=candidate.getAffiliation().getAffiliationName();
    }

}
