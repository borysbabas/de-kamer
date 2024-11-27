package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.AffiliationVotes;
import com.example.backend.entities.Candidate;
import com.example.backend.models.AffiliationVotesModel;
import com.example.backend.models.CandidateVotesModel;
import com.example.backend.models.VoteModel;
import com.example.backend.services.AffiliationVotesService;
import com.example.backend.services.CandidateService;
import com.example.backend.services.CountryVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;
    private final CountryVotesService countryVotesService;
    private final CandidateService candidateService;

    @Autowired
    public VotesController(AffiliationVotesService affiliationVotesService, CountryVotesService countryVotesService, CandidateService candidateService) {
        this.affiliationVotesService = affiliationVotesService;
        this.countryVotesService = countryVotesService;
        this.candidateService = candidateService;
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
//    @GetMapping(path="/votes/candidate/affiliation/{affiliationName}")
//    public List<CandidateVotesModel> getVotesByAffiliation(@PathVariable String affiliationName) {
//        List<Candidate> candidateList = candidateService.getCandidatesByAffiliationName(affiliationName);
//        List<CandidateVotesModel> candidateVotesList = new ArrayList<>();
//        for (Candidate candidate : candidateList) {
//            candidateVotesList.add(countryVotesService.getCountryVotesByCandidate(candidate));
//        }
//        return candidateVotesList;
//    }

    @GetMapping(path="/votes/candidate/affiliation/{affiliationName}")
    public List<CandidateVotesModel> getVotesByAffiliationId(@PathVariable String  affiliationName) {
        List<Candidate> candidateList = candidateService.getCandidatesByAffiliationName(affiliationName);
        Map<Integer, Candidate> candidateMap = new HashMap<>();
        List<CandidateVotesModel> candidateVotesList = new ArrayList<>();
        for (Candidate candidate : candidateList) {
            Candidate newCandidate = null;
            if (candidateMap.containsKey(candidate.hashCode())) {
                newCandidate = candidateMap.get(candidate.hashCode());
            } else {
                candidateMap.put(candidate.hashCode(), candidate);
                newCandidate = candidate;
            }
            candidateVotesList.add(countryVotesService.getCountryVotesByCandidate(newCandidate));
        }
        return candidateVotesList;
    }

    @GetMapping(path = ApiPaths.CANDIDATE_SERVICE_VOTES)
    public List<CandidateVotesModel> getTotalCandidateVotes() {

        return countryVotesService.getAllCountryVotes();
    }
}
