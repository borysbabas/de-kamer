package com.example.backend.services;

import com.example.backend.entities.PollingStation;
import com.example.backend.entities.PollingStationVotes;
import com.example.backend.repositories.PollingStationVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollingStationVotesService {
    private final PollingStationVotesRepository votesRepository;
    private final PollingStationVotesRepository pollingStationVotesRepository;

    @Autowired
    public PollingStationVotesService(PollingStationVotesRepository votesRepository, PollingStationVotesRepository pollingStationVotesRepository) {
        this.votesRepository = votesRepository;
        this.pollingStationVotesRepository = pollingStationVotesRepository;
    }

    public void addPollingStationVotes(PollingStationVotes pollingStationVotes) {
        votesRepository.save(pollingStationVotes);
    }
}
