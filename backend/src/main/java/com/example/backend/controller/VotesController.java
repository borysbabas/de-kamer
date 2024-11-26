package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.AffiliationVotes;
import com.example.backend.entities.Municipality;
import com.example.backend.models.*;
import com.example.backend.services.AffiliationVotesService;
import com.example.backend.services.CountryVotesService;
import com.example.backend.services.MunicipalityService;
import com.example.backend.services.MunicipalityVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;
    private final CountryVotesService countryVotesService;
    private final MunicipalityVotesService municipalityVotesService;
    private final MunicipalityService municipalityService;

    @Autowired
    public VotesController(AffiliationVotesService affiliationVotesService, CountryVotesService countryVotesService, MunicipalityVotesService municipalityVotesService, MunicipalityService municipalityService) {
        this.affiliationVotesService = affiliationVotesService;
        this.countryVotesService = countryVotesService;
        this.municipalityVotesService = municipalityVotesService;
        this.municipalityService = municipalityService;

    }

    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_VOTES)
    public List<AffiliationVotesModel> getTotalAffiliationVotes() {
        List<AffiliationVotes> affiliationVotes = affiliationVotesService.findAllAffiliationVotes();
        List<AffiliationVotesModel> affiliationVotesList = new ArrayList<>();
        for (AffiliationVotes affiliationVote : affiliationVotes) {
            affiliationVotesList.add(new AffiliationVotesModel(
                    affiliationVote.getAffiliation().getAffiliationName(),
                    affiliationVote.getAffiliationVotes(),
                    affiliationVote.getAffiliation().getAffiliationId()
            ));
        }
        return affiliationVotesList;
    }

    @GetMapping(path = ApiPaths.CANDIDATE_SERVICE_VOTES)
    public List<CandidateVotesModel> getTotalCandidateVotes() {
        return countryVotesService.getAllCountryVotes();
    }

    @GetMapping(path = ApiPaths.MUNICIPALITIES_SERVICE_VOTES)
    public List<MunicipalityVotesModel> getMunicipalityVotes() {
        return this.municipalityVotesService.getAll().stream().map(MunicipalityVotesModel::new).toList();
    }

    @PostMapping(path = ApiPaths.MUNICIPALITY_SERVICE_VOTES)
    public List<MunicipalityVotesModel> getMunicipalityVotesByMunicipalities(@RequestBody MunicipalityIdModel[] municipalityIdModels) {
        List<Municipality> municipalities = new ArrayList<>();
        for (MunicipalityIdModel municipalityIdModel : municipalityIdModels) {
            municipalities.add(this.municipalityService.getMunicipalityByMunicipalityId(municipalityIdModel.getMunicipalityId()));
        }

        return this.municipalityVotesService.getByMunicipalities(municipalities).stream().map(MunicipalityVotesModel::new).toList();
    }

}
