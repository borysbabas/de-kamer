package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "constituency_votes")
public class ConstituencyVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "constituency_id", referencedColumnName = "constituency_id")
    private Constituency constituency;

    @ManyToOne
    @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id")
    private Affiliation affiliation;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidate candidate;

    @Column(name = "total_votes")
    private Long totalVotes;

    public ConstituencyVotes() {
    }

    public ConstituencyVotes(Affiliation affiliation, Constituency constituency, Long totalVotes) {
        this.affiliation = affiliation;
        this.constituency = constituency;
        this.totalVotes = totalVotes;
    }
}