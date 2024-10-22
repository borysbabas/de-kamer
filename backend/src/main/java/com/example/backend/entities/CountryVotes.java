package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "country_votes")
public class CountryVotes {
    @OneToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    private Party party;

    @OneToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidate candidate;

    @Column(name = "total_votes")
    private int totalVotes;
}
