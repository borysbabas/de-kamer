package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.AffiliationVotes;
import com.example.backend.models.AffiliationVotesModel;
import com.example.backend.services.AffiliationVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;

    @Autowired
    public VotesController(AffiliationVotesService affiliationVotesService) {
        this.affiliationVotesService = affiliationVotesService;
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
}
