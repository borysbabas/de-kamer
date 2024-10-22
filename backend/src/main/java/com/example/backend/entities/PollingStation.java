package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "polling_stations")
public class PollingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "polling_station_id")
    private int pollingStationId;

    @ManyToOne
    @JoinColumn(name = "municipality_id", referencedColumnName = "municipality_id")
    private Municipality municipality;

    @Column(name = "polling_station_name")
    private String pollingStationName;
}