package com.example.backend.entities;

import com.example.backend.models.VoteModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "polling_station_votes")
public class PollingStationVotes {
    public PollingStationVotes() {}

    public PollingStationVotes(PollingStation pollingStation, Affiliation affiliation, Candidate candidate, Long totalVotes) {
        this.pollingStation = pollingStation;
        this.affiliation = affiliation;
        this.candidate = candidate;
        this.totalVotes = totalVotes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "polling_station_id", referencedColumnName = "polling_station_id")
    private PollingStation pollingStation;

    @ManyToOne
    @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id")
    private Affiliation affiliation;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidate candidate;

    @Column(name = "total_votes")
    private Long totalVotes;
}