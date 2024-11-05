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
    public Affiliation() {}

    public Affiliation(Long affiliationId, String affiliationName) {
        super();
        this.affiliationName = affiliationName;
        this.affiliationId = affiliationId;
    }


    @Id
    @Column(name = "affiliation_id")
    private Long affiliationId;

    @Column(name = "identical_lists")
    private boolean identicalLists;

    @Column(name = "affiliation_name")
    private String affiliationName;

    @OneToMany(mappedBy = "affiliation")
    private List<Candidate> candidates;
}