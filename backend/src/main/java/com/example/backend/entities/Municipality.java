package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "municipalities")
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "municipality_id")
    private int municipalityId;

    @ManyToOne
    @JoinColumn(name = "constituency_id", referencedColumnName = "constituency_id")
    private Constituency constituency;

    @Column(name = "municipality_name")
    private String municipalityName;

    @OneToMany(mappedBy = "municipality")
    private List<PollingStation> pollingStations;
}