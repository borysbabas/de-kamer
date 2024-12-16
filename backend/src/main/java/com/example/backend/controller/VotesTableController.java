package com.example.backend.controller;

import com.example.backend.DTO.MunicipalityVotesDTO;
import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import com.example.backend.entities.Municipality;
import com.example.backend.entities.MunicipalityVotes;
import com.example.backend.DTO.ConstituencyVotesDTO;
import com.example.backend.services.*;
import com.example.backend.config.ApiPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VotesTableController {
    private final ConstituencyService constituencyService;
    private final ConstituencyVotesService constituencyVotesService;
    private final MunicipalityService municipalityService;
    private final MunicipalityVotesService municipalityVotesService;
    private final PollingStationService pollingStationService;
    private final PollingStationVotesService pollingStationVotesService;

    @Autowired
    public VotesTableController(ConstituencyService constituencyService, ConstituencyVotesService constituencyVotesService, MunicipalityService municipalityService, MunicipalityVotesService municipalityVotesService, PollingStationService pollingStationService, PollingStationVotesService pollingStationVotesService) {
        this.constituencyService = constituencyService;
        this.constituencyVotesService = constituencyVotesService;
        this.municipalityService = municipalityService;
        this.municipalityVotesService = municipalityVotesService;
        this.pollingStationService = pollingStationService;
        this.pollingStationVotesService = pollingStationVotesService;
    }

    @GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE_VOTES_COUNT)
    public int getConstituencyVotesCountByConstituencyId(@PathVariable("constituencyId") Long constituencyId) {
        Constituency constituency = constituencyService.findConstituencyById(constituencyId);
        return constituencyVotesService.getConstituencyVotesCountByConstituency(constituency);
    }

    @GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE_VOTES)
    public List<ConstituencyVotesDTO> getConstituencyVotesByConstituencyIdByPage(@PathVariable("constituencyId") Long constituencyId, @PathVariable("pagination") Integer pagination, @PathVariable("page") Integer page) {
        Constituency constituency = constituencyService.findConstituencyById(constituencyId);
        List<ConstituencyVotes> constituencyVotes = constituencyVotesService.getConstituencyVotesByConstituencyByPage(constituency, pagination, page);
        List<ConstituencyVotesDTO> constituencyVotesList = new ArrayList<>();
        for (ConstituencyVotes constituencyVote : constituencyVotes) {
            constituencyVotesList.add(new ConstituencyVotesDTO(constituencyVote));
        }
        return constituencyVotesList;
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_SERVICE_VOTES_COUNT)
    public int getMunicipalityVotesCountByMunicipalityId(@PathVariable("municipalityId") Long municipalityId) {
        Municipality municipality = municipalityService.getMunicipalityByMunicipalityId(municipalityId);
        return municipalityVotesService.getMunicipalityVotesCountByMunicipality(municipality);
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_SERVICE_VOTES_BY_MUNICIPALITY)
    public List<MunicipalityVotesDTO> getMunicipalityVotesByConstituencyIdByPage(@PathVariable("municipalityId") Long municipalityId, @PathVariable("pagination") Integer pagination, @PathVariable("page") Integer page) {
        Municipality municipality = municipalityService.getMunicipalityByMunicipalityId(municipalityId);
        List<MunicipalityVotes> municipalityVotes = municipalityVotesService.getMunicipalityVotesByMunicipalityByPage(municipality, pagination, page);
        List<MunicipalityVotesDTO> municipalityVotesList = new ArrayList<>();
        for (MunicipalityVotes municipalityVote : municipalityVotes) {
            municipalityVotesList.add(new MunicipalityVotesDTO(municipalityVote));
        }
        return municipalityVotesList;
    }
}
