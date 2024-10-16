package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "municipalities")
public class Municipality {
    @Id
    @Column(name = "municipality_id")
    private int municipalityId;

    @ManyToOne
    @JoinColumn(name = "constituency_id", referencedColumnName = "constituency_id")
    private Constituency constituency;

    @Column(name = "municipality_name")
    private String municipalityName;
}