package com.example.backend.models;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import com.example.backend.entities.Constituency;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VoteModel {
    private Long affiliationId;
    private Long candidateId;
    private Long votes;
}
