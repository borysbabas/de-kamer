package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @Column(name = "candidate_id")
    private Long candidateId;

    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    private Party party;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "initials")
    private String initials;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "locality")
    private String locality;
}