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
    public Municipality() {}

    public Municipality(Long municipalityId, String municipalityName, Constituency constituency) {
        this.municipalityId = municipalityId;
        this.municipalityName = municipalityName;
        this.constituency = constituency;
    }

    @Id
    @Column(name = "municipality_id")
    private Long municipalityId;

    @ManyToOne
    @JoinColumn(name = "constituency_id", referencedColumnName = "constituency_id")
    private Constituency constituency;

    @Column(name = "municipality_name")
    private String municipalityName;

    @OneToMany(mappedBy = "municipality")
    private List<PollingStation> pollingStations;
}