package com.example.backend.controller;

import com.example.backend.DTO.CandidateDTO;
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

import java.util.List;
import java.util.stream.Collectors;

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
        return affiliationVotes.stream()
                .map(affiliationVote -> new AffiliationVotesModel(
                        affiliationVote.getAffiliation().getAffiliationName(),
                        affiliationVote.getAffiliationVotes(),
                        affiliationVote.getAffiliation().getAffiliationId()))
                .collect(Collectors.toList());
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

        List<CandidateVotesModel> allCandidateVotes = countryVotesService.getAllCountryVotes();
        List<CandidateDTO> candidates = candidateService.findCandidatesByAffiliationId(id)
                .stream()
                .map(candidate -> {
                    CandidateVotesModel candidateVotes = allCandidateVotes.stream()
                            .filter(cv -> cv.getCandidateId() == candidate.getCandidateId())
                            .findFirst()
                            .orElse(null);
                    Long votes = candidateVotes != null ? candidateVotes.getVotes() : 0L;
                    return new CandidateDTO(candidate, votes);
                })
                .collect(Collectors.toList());

        affiliationVotesModel.setCandidates(candidates);
        return affiliationVotesModel;
    }
}