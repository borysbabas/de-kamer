package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "parties")
public class Party {
    public Party() {

    }

    public Party(String partyName) {
        super();
        this.partyName = partyName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "party_id")
    private Long partyId;

    @Column(name = "party_name")
    private String partyName;
}