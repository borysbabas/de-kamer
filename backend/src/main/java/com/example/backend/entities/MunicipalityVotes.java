package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "municipality_votes")
public class MunicipalityVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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