package com.example.backend.controller;

import com.example.backend.DTO.CandidateDTO;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.*;
import com.example.backend.models.*;
import com.example.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;
    private final CountryVotesService countryVotesService;
    private final MunicipalityVotesService municipalityVotesService;
    private final MunicipalityService municipalityService;
    private final CandidateService candidateService;

    @Autowired
    public VotesController(
            AffiliationVotesService affiliationVotesService,
            CountryVotesService countryVotesService,
            CandidateService candidateService,
            MunicipalityVotesService municipalityVotesService,
            MunicipalityService municipalityService
    ) {
        this.affiliationVotesService = affiliationVotesService;
        this.countryVotesService = countryVotesService;
        this.candidateService = candidateService;
        this.municipalityVotesService = municipalityVotesService;
        this.municipalityService = municipalityService;

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

    @GetMapping(path = ApiPaths.CANDIDATE_VOTES_BY_AFFILIATION_NAME)
    public List<CandidateVotesModel> getVotesByAffiliationId(@PathVariable String affiliationName) {
        List<Candidate> candidateList = candidateService.getCandidatesByAffiliationName(affiliationName);
        Map<Integer, Candidate> candidateMap = new HashMap<>();
        List<CandidateVotesModel> candidateVotesList = new ArrayList<>();
        for (Candidate candidate : candidateList) {
            Candidate newCandidate = candidateMap.computeIfAbsent(candidate.hashCode(), k -> candidate);

            candidateVotesList.add(countryVotesService.getCountryVotesByCandidate(newCandidate));
        }
        return candidateVotesList;
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
        List<Municipality> municipalities = Arrays.stream(municipalityIdModels).map(municipalityIdModel ->
                this.municipalityService.getMunicipalityByMunicipalityId((municipalityIdModel.getMunicipalityId()))).toList();

        return this.municipalityVotesService.getByMunicipalities(municipalities).stream().map(MunicipalityVotesModel::new).toList();
    }

    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_GETBYID)
    public AffiliationVotesModel getAffiliationVotesById(@PathVariable("id") long id) {
        List<CandidateDTO> countryVotes = countryVotesService.getCandidateVotesByAffiliationId(id);
        AffiliationVotes affiliation = affiliationVotesService.findByAffiliationId(id);

        if (affiliation == null || countryVotes == null) {
            return null;
        }

        AffiliationVotesModel affiliationVotesModel = new AffiliationVotesModel(
                affiliation.getAffiliation().getAffiliationName(),
                affiliation.getAffiliationVotes(),
                affiliation.getAffiliation().getAffiliationId()
        );

        affiliationVotesModel.setCandidates(countryVotes);

        return affiliationVotesModel;
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_GET_AFFILIATION_VOTES)
    public List<MunicipalityAffiliationVotesModel> getMunicipalityAffiliationVotes(@PathVariable("municipalityId") long municipalityId) {
        return this.municipalityVotesService.getMunicipalityAffiliationVotes(municipalityId);
    }

    @GetMapping(path = ApiPaths.MUNCIIPALITY_GET_TOP_CANDIDATE)
    public CandidateVotesModel getTopCandidateByMunicipality(@PathVariable("municipalityId") long municipalityId) {
        return new CandidateVotesModel(this.municipalityVotesService.getTopMunicipalityByMunicipalityId(municipalityId));
    }
}