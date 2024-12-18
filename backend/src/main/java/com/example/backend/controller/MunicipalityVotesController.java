package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.models.MunicipalityVoteModel;
import com.example.backend.services.MunicipalityVotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MunicipalityVotesController {
    private final MunicipalityVotesService municipalityVotesService;

    public MunicipalityVotesController(MunicipalityVotesService municipalityVotesService){
        this.municipalityVotesService=municipalityVotesService;

    }
    @GetMapping(path= ApiPaths.MUNICIPALITY_SERVIVE_GETMUNICIPALITYVOTESBYCANDIDATEID)
    public List<MunicipalityVoteModel> getMunicipalitiesByCandidateId(@PathVariable("candidateId")Long candidateId){
        return municipalityVotesService.getTop5MunicipalitiesByCandidateId(candidateId);
    }

    @GetMapping(path= ApiPaths.MUNICIPALITY_SERVICE_GET_MUNICIPALITYVOTES_BY_AFFILIATIONID)
    public List<MunicipalityVoteModel> getMunicipalitiesByAffiliationId(@PathVariable("affiliationId") Long affiliationId) {
        return municipalityVotesService.getMunicipalitiesByAffiliationId(affiliationId);
    }
}
