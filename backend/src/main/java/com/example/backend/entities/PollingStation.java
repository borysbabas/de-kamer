package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "polling_stations")
public class PollingStation {
    public PollingStation() {}

    public PollingStation(Long id, String name, String zipCode) {
        this.pollingStationNumber = id;
        this.pollingStationName = name;
        this.zipCode = zipCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "polling_station_id")
    private Long id;

    @Column(name = "polling_station_number")
    private Long pollingStationNumber;

    @ManyToOne
    @JoinColumn(name = "municipality_id", referencedColumnName = "municipality_id")
    private Municipality municipality;

    @Column(name = "polling_station_name")
    private String pollingStationName;

    @Column(name = "zip_code")
    private String zipCode;
}