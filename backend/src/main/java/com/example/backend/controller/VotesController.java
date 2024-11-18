package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.AffiliationVotes;
import com.example.backend.entities.ConstituencyVotes;
import com.example.backend.models.AffiliationVotesModel;
import com.example.backend.models.ConstituencyVotesModel;
import com.example.backend.services.AffiliationVotesService;
import com.example.backend.services.ConstituencyVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;
    private final ConstituencyVotesService constituencyVotesService;

    @Autowired
    public VotesController(AffiliationVotesService affiliationVotesService, ConstituencyVotesService constituencyVotesService) {
        this.affiliationVotesService = affiliationVotesService;
        this.constituencyVotesService = constituencyVotesService;
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

    @GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE_VOTES)
    public List<ConstituencyVotesModel> getTotalConstituencyVotes() {
        List<ConstituencyVotes> constituencyVotes = constituencyVotesService.getAllConstituencyVotes();
        List<ConstituencyVotesModel> constituencyVotesList = new ArrayList<>();
        for (ConstituencyVotes constituencyVote : constituencyVotes) {
            constituencyVotesList.add(new ConstituencyVotesModel(
                constituencyVote.getConstituency().getConstituencyName(),
                constituencyVote.getAffiliation().getAffiliationName(),
                constituencyVote.getCandidate().getFullName(),
                constituencyVote.getTotalVotes(),
                constituencyVote.getConstituency().getConstituencyId(),
                constituencyVote.getAffiliation().getAffiliationId(),
                constituencyVote.getCandidate().getCandidateId()
            ));
        }
        return constituencyVotesList;
    }
}
