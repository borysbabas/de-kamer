package com.example.backend.controller;

import com.example.backend.DTO.MunicipalityVotesDTO;
import com.example.backend.DTO.PollingStationVotesDTO;
import com.example.backend.entities.*;
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

    /**
     * Endpoint to send integer containing number of candidates with at least 1 vote in given constituency to frontend
     *
     * @param constituencyId Path variable, id of constituency to get number of candidates with votes of
     * @return Number of candidates with at least 1 vote in the given constituency
     */
    @GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE_VOTES_COUNT)
    public int getConstituencyVotesCountByConstituencyId(@PathVariable("constituencyId") Long constituencyId) {
        Constituency constituency = constituencyService.findConstituencyById(constituencyId);
        return constituencyVotesService.getConstituencyVotesCountByConstituency(constituency);
    }

    /**
     * Endpoint to send limited list of constituency votes of given constituency and page to frontend
     *
     * @param constituencyId Path variable, id of constituency to get the votes of
     * @param pagination Path variable, number of rows to get from database
     * @param page Path variable, selected table page of source element
     * @return List of limited number of constituency votes from given position in the database
     */
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

    /**
     * Endpoint to send integer containing number of candidates with at least 1 vote in given municipality to frontend
     *
     * @param municipalityId Path variable, id of municipality to get number of candidates with votes of
     * @return Number of candidates with at least 1 vote in the given municipality
     */
    @GetMapping(path = ApiPaths.MUNICIPALITY_SERVICE_VOTES_COUNT)
    public int getMunicipalityVotesCountByMunicipalityId(@PathVariable("municipalityId") Long municipalityId) {
        Municipality municipality = municipalityService.getMunicipalityByMunicipalityId(municipalityId);
        return municipalityVotesService.getMunicipalityVotesCountByMunicipality(municipality);
    }

    /**
     * Endpoint to send limited list of municipality votes of given municipality and page to frontend
     *
     * @param municipalityId Path variable, id of municipality to get the votes of
     * @param pagination Path variable, number of rows to get from database
     * @param page Path variable, selected table page of source element
     * @return List of limited number of municipality votes from given position in the database
     */
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

    /**
     * Endpoint to send integer containing number of candidates with at least 1 vote in given polling station to frontend
     *
     * @param pollingStationId Path variable, id of polling station to get number of candidates with votes of
     * @return Number of candidates with at least 1 vote in the given polling station
     */
    @GetMapping(path = ApiPaths.POLLING_STATION_SERVICE_VOTES_COUNT)
    public int getPollingStationVotesCountByPollingStationId(@PathVariable("pollingStationId") Long pollingStationId) {
        PollingStation pollingStation = pollingStationService.getPollingStationById(pollingStationId);
        return pollingStationVotesService.getPollingStationVotesCountByPollingStation(pollingStation);
    }

    /**
     * Endpoint to send limited list of polling station votes of given polling station and page to frontend
     *
     * @param pollingStationId Path variable, id of polling station to get the votes of
     * @param pagination Path variable, number of rows to get from database
     * @param page Path variable, selected table page of source element
     * @return List of limited number of polling station votes from given position in the database
     */
    @GetMapping(path = ApiPaths.POLLING_STATION_SERVICE_VOTES)
    public List<PollingStationVotesDTO> getPollingStationVotesByPollingStationIdByPage(@PathVariable("pollingStationId") Long pollingStationId, @PathVariable("pagination") Integer pagination, @PathVariable("page") Integer page) {
        PollingStation pollingStation = pollingStationService.getPollingStationById(pollingStationId);
        List<PollingStationVotesDTO> pollingStationVotesList = new ArrayList<>();
        List<PollingStationVotes> pollingStationVotes = pollingStationVotesService.getPollingStationVotesByPollingStationByPage(pollingStation, pagination, page);
        for (PollingStationVotes pollingStationVote : pollingStationVotes) {
            pollingStationVotesList.add(new PollingStationVotesDTO(pollingStationVote));
        }
        return pollingStationVotesList;
    }
}
