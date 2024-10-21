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

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "initials", nullable = false)
    private String initials;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name_prefix")
    private String lastNamePrefix;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "locality", nullable = false)
    private String locality;

    /**
     * check if two objects are equal.
     * They are equal if the initials, lastname, locality and party are equal
     * @param o The object to check
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        // if both the object references are
        // referring to the same object.
        if (this == o) return true;

        // checks if the object is null or not of the same class
        if (o == null || this.getClass() != o.getClass()) return false;

        Candidate candidate = (Candidate) o;

        return this.hashCode() == candidate.hashCode();
    }

    /**
     * hashCode based on equal values of:
     * - initials
     * - last name
     * - locality
     * - party
     * @return the newly made hashcode
     */
    @Override
    public int hashCode() {
        return initials.hashCode() + lastName.hashCode() + locality.hashCode() + party.getPartyId().hashCode();
    }

}