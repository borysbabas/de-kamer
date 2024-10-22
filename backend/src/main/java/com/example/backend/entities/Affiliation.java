package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "affiliations")
public class Affiliation {
    public Affiliation() {

    }

    public Affiliation(Long affiliationIdentifier, String affiliationName) {
        super();
        this.affiliationIdentifier = affiliationIdentifier;
        this.affiliationName = affiliationName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", initialValue = 0, allocationSize = 1)
    @Column(name = "affiliation_id")
    private Long affiliationId;

    @Column(name = "affiliation_identifier")
    private Long affiliationIdentifier;

    @Column(name = "affiliation_name")
    private String affiliationName;

    @OneToMany(mappedBy = "affiliation")
    private List<Municipality> municipalities;
}