package com.example.backend.controller;

import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.DTO.CandidateDTO;
import com.example.backend.entities.Candidate;
import com.example.backend.models.CandidateVotesModel;
import com.example.backend.services.CandidateService;
import com.example.backend.services.CountryVotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CandidateController {
    private final CandidateService candidateService;
    private final CountryVotesService countryVotesService;
    private final ApiConfig apiConfig;

    public CandidateController(CandidateService candidateService, CountryVotesService countryVotesService, ApiConfig apiConfig) {
        this.candidateService = candidateService;
        this.countryVotesService = countryVotesService;
        this.apiConfig = apiConfig;
    }

    @GetMapping(path = "/candidates", produces = "application/json")
    public List<CandidateDTO> getAllCandidates(){
        return candidateService.findAll()
                .stream()
                .map(CandidateDTO::new)
                .collect(Collectors.toList());
//
//        List<Candidate> candidates = candidateService.findAll();
//        List<CandidateDTO> candidateDTOS = new ArrayList<>();
//        for (Candidate candidate : candidates) {
//            candidateDTOS.add(new CandidateDTO(candidate));
//        }
//        return candidateDTOS;
    }
    @GetMapping(path="/candidates/affiliation/{affiliationName}", produces = "application/json")
    public List<CandidateDTO> getCandidatesByAffiliationName(@PathVariable("affiliationName") String affiliationName){
        return candidateService.getCandidatesByAffiliationName(affiliationName)
                .stream()
                .map(CandidateDTO::new)
                .collect(Collectors.toList());
    }


    @GetMapping(path="/candidates/{candidateId}")
    public CandidateVotesModel getCandidateById(@PathVariable("candidateId") Long candidateId){
        Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
        return countryVotesService.getCountryVotesByCandidate(candidate);

    }
}
