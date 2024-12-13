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
public class ChartController {
    private final CandidateService candidateService;
    private final CountryVotesService countryVotesService;

    @Autowired
    public ChartController(
            CandidateService candidateService,
            CountryVotesService countryVotesService
    ) {
        this.candidateService = candidateService;
        this.countryVotesService = countryVotesService;
    }


    @GetMapping(path = "/chart/candidates-votes/{affiliationName}")
    public Map<String, Object> getVotesByAffiliationId(@PathVariable String affiliationName) {
        List<Candidate> candidateList = candidateService.getCandidatesByAffiliationName(affiliationName);
        Map<Integer, Candidate> candidateMap = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();

        for (Candidate candidate : candidateList) {
            Candidate newCandidate = null;
            if (candidateMap.containsKey(candidate.hashCode())) {
                newCandidate = candidateMap.get(candidate.hashCode());
            } else {
                candidateMap.put(candidate.hashCode(), candidate);
                newCandidate = candidate;
            }
            CandidateVotesModel candidateVotes = countryVotesService.getCountryVotesByCandidate(newCandidate);
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("x", candidateVotes.getFullName(newCandidate.getInitials(), newCandidate.getLastNamePrefix(), newCandidate.getLastName()));
            dataPoint.put("y", candidateVotes.getVotes());
            data.add(dataPoint);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("name", "Stemmen");
        response.put("data", data);

        return response;
    }


}
