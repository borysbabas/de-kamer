package com.example.backend.controller;

import com.example.backend.DTO.CandidateDTO;
import com.example.backend.entities.Candidate;
import com.example.backend.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CandidateController {
    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping(path = "/candidates", produces = "application/json")
    public List<CandidateDTO> getAllCandidates() {
        return candidateService.findAll()
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
        List<Candidate> candidates = candidateService.findCandidatesByAffiliationId(affiliationId);
        return candidates.stream()
                .map(CandidateDTO::new)
                .collect(Collectors.toList());
    }
}