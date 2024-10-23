package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "country_votes")
public class CountryVotes {
    public CountryVotes() {

    }

    public CountryVotes(Long votes, Candidate candidate) {
        super();
        this.totalVotes = votes;
        this.candidate = candidate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "country_votes_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidate candidate;

    @Column(name = "total_votes")
    private Long totalVotes;
}
