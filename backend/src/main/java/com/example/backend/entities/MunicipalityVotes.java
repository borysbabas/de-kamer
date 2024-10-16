package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "munipality_votes")
public class MunicipalityVotes {
    @ManyToOne
    @JoinColumn(name = "municipality_id", referencedColumnName = "municipality_id")
    private Municipality municipality;

    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    private Party party;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidate candidate;

    @Column(name = "total_votes")
    private int totalVotes;
}