package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "candidates")
public class Candidate {
    public Candidate() {

    }

    public Candidate(Party party, Long memberId, String initials, String firstName, String lastName, String gender, String locality) {
        this.party = party;
        this.memberId = memberId;
        this.initials = initials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.locality = locality;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "candidate_id")
    private Long candidateId;

    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    private Party party;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "initials")
    private String initials;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "locality")
    private String locality;

}