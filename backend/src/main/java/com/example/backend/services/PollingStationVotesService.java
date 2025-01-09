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

    public int getPollingStationVotesCountByPollingStation(PollingStation pollingStation) {
        return pollingStationVotesRepository.countByPollingStation(pollingStation);
    }

    public List<PollingStationVotes> getPollingStationVotesByPollingStationByPage(PollingStation pollingStation, int pagination, int page) {
        // Page number gets transformed to proper offset by subtracting 1 of the page number and multiplying by the number of rows on a page
        return pollingStationVotesRepository.findByPollingStationByPageOrderByTotalVotesDesc(pollingStation, pagination, (page -1) * pagination);
    }
}
