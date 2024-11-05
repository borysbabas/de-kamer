package com.example.backend.controller;

import com.example.backend.DTO.CandidateDTO;
import com.example.backend.entities.Candidate;
import com.example.backend.services.CandidateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
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

}
