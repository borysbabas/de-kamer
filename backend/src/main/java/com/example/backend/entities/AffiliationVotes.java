package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "affiliation_votes")
public class AffiliationVotes {
    public AffiliationVotes() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long voteId;

    @Column(name = "affiliation_votes")
    private Long affiliationVotes;

    @OneToOne
    private Affiliation affiliation;

}
