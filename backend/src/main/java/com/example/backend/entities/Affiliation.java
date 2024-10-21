package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "affiliations")
public class Affiliation {
    public Affiliation() {

    }

    public Affiliation(String affiliationName) {
        super();
        this.affiliationName = affiliationName;
    }
    public Affiliation(Long id, String affiliationName) {
        super();
        this.affiliationId = id;
        this.affiliationName = affiliationName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", initialValue = 0)
    @Column(name = "affiliation_id")
    private Long affiliationId;

    @Column(name = "affiliation_identifier")
    private Long affiliationIdentifier;

    @Column(name = "affiliation_name")
    private String affiliationName;
}