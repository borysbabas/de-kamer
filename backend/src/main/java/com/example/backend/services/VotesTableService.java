package com.example.backend.services;

import com.example.backend.entities.*;
import com.example.backend.repositories.ConstituencyVotesRepository;
import com.example.backend.repositories.MunicipalityVotesRepository;
import com.example.backend.repositories.PollingStationVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotesTableService {
    private final ConstituencyVotesRepository constituencyVotesRepository;
    private final MunicipalityVotesRepository municipalityVotesRepository;
    private final PollingStationVotesRepository pollingStationVotesRepository;

    @Autowired
    public VotesTableService(ConstituencyVotesRepository constituencyVotesRepository, MunicipalityVotesRepository municipalityVotesRepository, PollingStationVotesRepository pollingStationVotesRepository) {
        this.constituencyVotesRepository = constituencyVotesRepository;
        this.municipalityVotesRepository = municipalityVotesRepository;
        this.pollingStationVotesRepository = pollingStationVotesRepository;
    }

    public int getConstituencyVotesCountByConstituency(Constituency constituency) {
        return constituencyVotesRepository.countByConstituency(constituency);
    }

    public List<ConstituencyVotes> getConstituencyVotesByConstituencyByPage(Constituency constituency, int pagination, int page) {
        return constituencyVotesRepository.findByConstituencyByPageOrderByTotalVotesDesc(constituency, pagination, (page - 1) * pagination);
    }

    public int getMunicipalityVotesCountByMunicipality(Municipality municipality) {
        return municipalityVotesRepository.countMunicipalityVotesByMunicipality(municipality);
    }

    public List<MunicipalityVotes> getMunicipalityVotesByMunicipalityByPage(Municipality municipality, int pagination, int page) {
        return municipalityVotesRepository.findByMunicipalityByPageOrderByTotalVotesDesc(municipality, pagination, (page - 1) * pagination);
    }

    public int getPollingStationVotesCountByPollingStation(PollingStation pollingStation) {
        return pollingStationVotesRepository.countByPollingStation(pollingStation);
    }

    public List<PollingStationVotes> getPollingStationVotesByPollingStationByPage(PollingStation pollingStation, int pagination, int page) {
        return pollingStationVotesRepository.findByPollingStationByPageOrderByTotalVotesDesc(pollingStation, pagination, (page -1) * pagination);
    }
}
