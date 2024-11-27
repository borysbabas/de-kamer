package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VoteModel {
    private Long affiliationId;
    private Long candidateId;
    private Long votes;
}
