package com.example.backend.controller;

// Controller for made for chart data (autofix dataformat)
//*
// Dataformat:
// {
//     "data": [
//         {
//             name: "Data-Set-Name"
//             data: [
//                  {
//                     x: "Name-value",
//                     y: int-value
//                  },
//                 ],
//         },

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.*;
import com.example.backend.models.*;
import com.example.backend.services.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
@RequestMapping(path = ApiPaths.CHART_SERVICE)
public class ChartController {
    private final CandidateService candidateService;
    private final CountryVotesService countryVotesService;
    private final AffiliationService affiliationService;
    private final AffiliationVotesService affiliationVotesService;

    @Autowired
    public ChartController(
            CandidateService candidateService,
            CountryVotesService countryVotesService,
            AffiliationService affiliationService,
            AffiliationVotesService affiliationVotesService
    ) {
        this.candidateService = candidateService;
        this.countryVotesService = countryVotesService;
        this.affiliationService = affiliationService;
        this.affiliationVotesService = affiliationVotesService;
    }


    @GetMapping(path = ApiPaths.CANDIDATE_SERVICE_GET_AFFILIATION_VOTES)
    public Map<String, Object> getVotesByAffiliationId(@PathVariable Long affiliationId) {
        List<Candidate> candidateList = candidateService.getCandidatesByAffiliationId(affiliationId);
        Map<Integer, Candidate> candidateMap = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();

        for (Candidate candidate : candidateList) {
            Candidate newCandidate = candidateMap.computeIfAbsent(candidate.hashCode(), k -> candidate);
            CandidateVotesModel candidateVotes = countryVotesService.getCountryVotesByCandidate(newCandidate);
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("x", candidateVotes.getFullName(newCandidate.getInitials(), newCandidate.getLastNamePrefix(), newCandidate.getLastName()));
            dataPoint.put("y", candidateVotes.getVotes());
            dataPoint.put("id", candidateVotes.getCandidateId());
            data.add(dataPoint);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("name", "Stemmen");
        response.put("data", data);

        return response;
    }

    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_MEMBER_COUNT)
    public Map<String, Object> getAllMemberCount() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<Affiliation> affiliations = affiliationService.getAllAffiliations();

        for (Affiliation affiliation : affiliations) {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("x", affiliation.getAffiliationName());
            dataPoint.put("y", candidateService.countDistinctCandidatesByAffiliation(affiliation));
            dataPoint.put("id", affiliation.getAffiliationId());
            data.add(dataPoint);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("name", "Leden");
        response.put("data", data);

        return response;
    }

    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_VOTES)
    public Map<String, Object> getTotalAffiliationVotes() {
        List<Map<String, Object>> data = new ArrayList<>();
        List<AffiliationVotes> affiliationVotes = affiliationVotesService.findAllAffiliationVotes();

        for (AffiliationVotes affiliationVote : affiliationVotes) {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("x", affiliationVote.getAffiliation().getAffiliationName());
            dataPoint.put("y", affiliationVote.getAffiliationVotes());
            dataPoint.put("id", affiliationVote.getAffiliation().getAffiliationId());
            data.add(dataPoint);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("name", "Stemmen");
        response.put("data", data);

        return response;
    }


}
