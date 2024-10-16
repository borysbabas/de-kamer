package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "polling_station_votes")
public class PollingStationVotes {
    @ManyToOne
    @JoinColumn(name = "polling_station_id", referencedColumnName = "polling_station_id")
    private PollingStation pollingStation;

    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    private Party party;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidate candidate;

    @Column(name = "total_votes")
    private int totalVotes;
}