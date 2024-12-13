package com.example.backend.controller;

import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.DTO.CandidateDTO;
import com.example.backend.entities.Candidate;
import com.example.backend.models.CandidateVotesModel;
import com.example.backend.services.AffiliationService;
import com.example.backend.services.CandidateService;
import com.example.backend.services.CountryVotesService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AffiliationService affiliationService;

    @Autowired
    public CandidateController(CandidateService candidateService, CountryVotesService countryVotesService, ApiConfig apiConfig, AffiliationService affiliationService) {
        this.candidateService = candidateService;
        this.countryVotesService = countryVotesService;
        this.apiConfig = apiConfig;
        this.affiliationService = affiliationService;
    }

    @GetMapping(path = "/candidates", produces = "application/json")
    public List<CandidateDTO> getAllCandidates() {
        return candidateService.findAll()
                .stream()
                .map(CandidateDTO::new)
                .collect(Collectors.toList());
    }
    @GetMapping(path="/candidates/affiliation/{affiliationName}", produces = "application/json")
    public List<CandidateDTO> getCandidatesByAffiliationName(@PathVariable("affiliationName") String affiliationName){
        return candidateService.getCandidatesByAffiliationName(affiliationName)
                .stream()
                .map(CandidateDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/candidates/{candidateId}")
    public CandidateDTO getCandidateById(@PathVariable("candidateId") Long candidateId) {
        Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
        return new CandidateDTO(candidate);
    }

    @GetMapping(path = "/party/{id}/candidates", produces = "application/json")
    public List<CandidateDTO> getCandidatesByAffiliationId(@PathVariable("id") Long affiliationId) {
        List<Candidate> candidates = candidateService.findCandidatesByAffiliation(
                this.affiliationService.findAffiliationByAffiliationId(affiliationId)
        );
        return candidates.stream()
                .map(CandidateDTO::new)
                .collect(Collectors.toList());
    }
}