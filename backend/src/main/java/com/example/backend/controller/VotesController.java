package com.example.backend.controller;

import com.example.backend.DTO.CandidateDTO;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.AffiliationVotes;
import com.example.backend.entities.Municipality;
import com.example.backend.models.*;
import com.example.backend.services.AffiliationVotesService;
import com.example.backend.services.CountryVotesService;
import com.example.backend.services.MunicipalityService;
import com.example.backend.services.MunicipalityVotesService;
import com.example.backend.entities.Candidate;
import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import com.example.backend.models.AffiliationVotesModel;
import com.example.backend.models.CandidateVotesModel;
import com.example.backend.services.*;
import com.example.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class VotesController {
    private final AffiliationVotesService affiliationVotesService;
    private final ConstituencyService constituencyService;
    private final ConstituencyVotesService constituencyVotesService;
    private final CountryVotesService countryVotesService;
    private final MunicipalityVotesService municipalityVotesService;
    private final MunicipalityService municipalityService;
    private final CandidateService candidateService;

    // Constant to define the amount of elements loaded into the votes table
    private static final int PAGINATION = 30;

    @Autowired
    public VotesController(
            AffiliationVotesService affiliationVotesService,
            CountryVotesService countryVotesService,
            CandidateService candidateService,
            ConstituencyService constituencyService,
            ConstituencyVotesService constituencyVotesService,
            MunicipalityVotesService municipalityVotesService,
            MunicipalityService municipalityService
    ) {
        this.affiliationVotesService = affiliationVotesService;
        this.constituencyService = constituencyService;
        this.constituencyVotesService = constituencyVotesService;
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
                constituencyVotesList.add(new ConstituencyVotesModel(constituencyVote));
            }
        }
        return constituencyVotesList;
    }
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

        List<Candidate> candidates = candidateService.findCandidatesByAffiliation(affiliationVotes.getAffiliation());

        affiliationVotesModel.setCandidates(candidates.stream().map(candidate -> new CandidateDTO(this.countryVotesService.findByCandidate(candidate))).toList());
        return affiliationVotesModel;
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_GET_AFFILIATION_VOTES)
    public List<MunicipalityAffiliationVotesModel> getMunicipalityAffiliationVotes(@PathVariable("municipalityId") long municipalityId) {
        return this.municipalityVotesService.getMunicipalityAffiliationVotes(municipalityId);
    }

    @GetMapping(path = "/votes/municipality/{municipalityId}/top")
    public CandidateDTO getTopCandidateByMunicipality(@PathVariable("municipalityId") long municipalityId) {
        return new CandidateDTO(this.municipalityVotesService.getTopMunicipalityByMunicipalityId(municipalityId));
    }
}