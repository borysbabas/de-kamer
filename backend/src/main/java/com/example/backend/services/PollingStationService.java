package com.example.backend.services;

import com.example.backend.entities.PollingStation;
import com.example.backend.repositories.PollingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollingStationService {
    private final PollingStationRepository pollingStationRepository;

    @Autowired
    public PollingStationService(PollingStationRepository pollingStationRepository) {
        this.pollingStationRepository = pollingStationRepository;
    }

    public void addPollingStation(PollingStation pollingStation) {
        pollingStationRepository.save(pollingStation);
    }
}
