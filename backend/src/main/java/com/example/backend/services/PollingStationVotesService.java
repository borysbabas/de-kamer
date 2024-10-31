package com.example.backend.services;

import com.example.backend.entities.PollingStationVotes;
import com.example.backend.repositories.PollingStationVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollingStationVotesService {
    private final PollingStationVotesRepository votesRepository;

    @Autowired
    public PollingStationVotesService(PollingStationVotesRepository votesRepository) {
        this.votesRepository = votesRepository;
    }

    public void addPollingStationVotes(PollingStationVotes pollingStationVotes) {
        votesRepository.save(pollingStationVotes);
    }
}
