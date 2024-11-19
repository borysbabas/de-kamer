package com.example.backend.controller;

import com.example.backend.entities.MunicipalityVotes;
import com.example.backend.services.MunicipalityVotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MunicipalityVotesController {
    private final MunicipalityVotesService municipalityVotesService;

    public MunicipalityVotesController(MunicipalityVotesService municipalityVotesService){
        this.municipalityVotesService=municipalityVotesService;

    }
    @GetMapping(path="/municipality/{candidateId}")
    public MunicipalityVotes getMunicipalityByCandidateId(@PathVariable("candidateId")Long candidateId){
        return municipalityVotesService.getMunicipalityVotesByCandidateId(candidateId);
    }

}
