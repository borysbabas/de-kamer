package com.example.backend.controller;

import com.example.backend.models.AffiliationVotes;
import com.example.backend.services.AffiliationService;
import com.example.backend.services.AffiliationVotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/votes")
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;

    public VotesController(AffiliationVotesService affiliationVotesService) {
        this.affiliationVotesService = affiliationVotesService;
    }

    @GetMapping(path = "/total/affiliation")
    public List<AffiliationVotes> getTotalAffiliationVotes() {
        List<com.example.backend.entities.AffiliationVotes> affiliationVotes = affiliationVotesService.findAllAffiliationVotes();
        List<AffiliationVotes> affiliationVotesList = new ArrayList<>();
        for (com.example.backend.entities.AffiliationVotes affiliationVote : affiliationVotes) {
            affiliationVotesList.add(new AffiliationVotes(
                    affiliationVote.getAffiliation().getAffiliationName(),
                    affiliationVote.getAffiliationVotes()
            ));
        }

        return affiliationVotesList;

    }
}
