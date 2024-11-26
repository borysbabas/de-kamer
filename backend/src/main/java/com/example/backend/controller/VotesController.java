package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.AffiliationVotes;
import com.example.backend.models.AffiliationVotesModel;
import com.example.backend.models.CandidateVotesModel;
import com.example.backend.services.AffiliationVotesService;
import com.example.backend.services.CandidateService;
import com.example.backend.services.CountryVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;
    private final CountryVotesService countryVotesService;

    @Autowired
    public VotesController(AffiliationVotesService affiliationVotesService, CountryVotesService countryVotesService, CandidateService candidateService) {
        this.affiliationVotesService = affiliationVotesService;
        this.countryVotesService = countryVotesService;
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

    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_GETBYID)
    public AffiliationVotesModel getAffiliationVotesById(@PathVariable("id") long id) {
        AffiliationVotes affiliationVotes = affiliationVotesService.findByAffiliationId(id);
        if (affiliationVotes == null) {
            return null;
        }
        return new AffiliationVotesModel(
                affiliationVotes.getAffiliation().getAffiliationName(),
                affiliationVotes.getAffiliationVotes(),
                affiliationVotes.getAffiliation().getAffiliationId());
    }
}