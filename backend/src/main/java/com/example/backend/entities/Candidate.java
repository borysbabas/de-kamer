package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "candidates")
public class Candidate {
    public Candidate() {}

    public Candidate(Candidate candidate) {
        this.candidateId = candidate.getCandidateId();
        this.affiliation = candidate.getAffiliation();
        this.memberId = candidate.getMemberId();
        this.initials = candidate.getInitials();
        this.firstName = candidate.getFirstName();
        this.lastName = candidate.getLastName();
        this.lastNamePrefix = candidate.getLastNamePrefix();
        this.gender = candidate.getGender();
        this.locality = candidate.getLocality();
        this.constituency = candidate.getConstituency();
        this.shortCode = candidate.getShortCode();
    }

    public Candidate(Affiliation affiliation, Constituency constituency) {
        this.affiliation = affiliation;
        this.constituency = constituency;
    }

    public Candidate(Affiliation affiliation, Long memberId, String initials, String firstName, String lastName, String gender, String locality) {
        this.affiliation = affiliation;
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
    @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id")
    private Affiliation affiliation;

    @ManyToOne
    @JoinColumn(name = "constituency_id", referencedColumnName = "constituency_id")
    private Constituency constituency;

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

    @Column(name = "short_code")
    private String shortCode;

    public String getFullName() {
        return lastNamePrefix == null || lastNamePrefix.isEmpty()
                ? "%s %s".formatted(firstName == null ? initials : firstName, lastName)
                : "%s %s %s".formatted(firstName == null ? initials : firstName, lastNamePrefix, lastName);
    }

    /**
     * check if two objects are equal.
     * They are equal if the initials, lastname, locality and affiliation are equal
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
     * - affiliation
     * @return the newly made hashcode
     */
    @Override
    public int hashCode() {
        return initials.hashCode() + lastName.hashCode() + locality.hashCode() + affiliation.getAffiliationId().hashCode();
    }

}