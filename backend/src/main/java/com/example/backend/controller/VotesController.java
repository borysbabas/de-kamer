package com.example.backend.controller;

import com.example.backend.DTO.CandidateDTO;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.AffiliationVotes;
import com.example.backend.entities.Candidate;
import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import com.example.backend.models.AffiliationVotesModel;
import com.example.backend.models.CandidateVotesModel;
import com.example.backend.models.ConstituencyVotesModel;
import com.example.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;
    private final ConstituencyService constituencyService;
    private final ConstituencyVotesService constituencyVotesService;
    private final CountryVotesService countryVotesService;
    private final CandidateService candidateService;

    // Constant to define the amount of elements loaded into the votes table
    private static final int PAGINATION = 30;

    @Autowired
    public VotesController(AffiliationVotesService affiliationVotesService, CountryVotesService countryVotesService, CandidateService candidateService, ConstituencyService constituencyService, ConstituencyVotesService constituencyVotesService) {
        this.affiliationVotesService = affiliationVotesService;
        this.constituencyService = constituencyService;
        this.constituencyVotesService = constituencyVotesService;
        this.countryVotesService = countryVotesService;
        this.candidateService = candidateService;
    }

    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_VOTES)
    public List<AffiliationVotesModel> getTotalAffiliationVotes() {
        List<AffiliationVotes> affiliationVotes = affiliationVotesService.findAllAffiliationVotes();
        return affiliationVotes.stream()
                .map(affiliationVote -> new AffiliationVotesModel(
                        affiliationVote.getAffiliation().getAffiliationName(),
                        affiliationVote.getAffiliationVotes(),
                        affiliationVote.getAffiliation().getAffiliationId()))
                .collect(Collectors.toList());
    }

    @GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE_VOTES + "/{constituencyId}/count")
    public int getConstituencyVotesCountByConstituencyId(@PathVariable("constituencyId") Long constituencyId) {
        Constituency constituency = constituencyService.findConstituencyById(constituencyId);
        return constituencyVotesService.getConstituencyVotesCountByConstituency(constituency);
    }

    @GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE_VOTES + "/{constituencyId}/{page}")
    public List<ConstituencyVotesModel> getConstituencyVotesByConstituencyIdByPage(@PathVariable("constituencyId") Long constituencyId, @PathVariable("page") Integer page) {
        Constituency constituency = constituencyService.findConstituencyById(constituencyId);
        List<ConstituencyVotes> constituencyVotes = constituencyVotesService.getConstituencyVotesByConstituencyByPage(constituency, PAGINATION, page);
        List<ConstituencyVotesModel> constituencyVotesList = new ArrayList<>();
        for (ConstituencyVotes constituencyVote : constituencyVotes) {
            if (!constituencyVote.getConstituency().getConstituencyName().equals("alle") && !constituencyVote.getConstituency().getConstituencyName().equals("Bonaire")) {
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
        }
        return constituencyVotesList;
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
        AffiliationVotesModel affiliationVotesModel = new AffiliationVotesModel(
                affiliationVotes.getAffiliation().getAffiliationName(),
                affiliationVotes.getAffiliationVotes(),
                affiliationVotes.getAffiliation().getAffiliationId());

        List<Candidate> candidates = candidateService.findDistinctByAffiliation(affiliationVotes.getAffiliation());

        affiliationVotesModel.setCandidates(candidates.stream().map(candidate -> new CandidateDTO(this.countryVotesService.findByCandidate(candidate))).toList());
        return affiliationVotesModel;
    }
}