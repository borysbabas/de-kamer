package com.example.backend.services;

import com.example.backend.entities.Candidate;
import com.example.backend.entities.CountryVotes;
import com.example.backend.models.CandidateVotesModel;
import com.example.backend.repositories.CountryVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryVotesService {
    private final CountryVotesRepository countryVotesRepository;
    private final CandidateService candidateService;

    @Autowired
    public CountryVotesService(CountryVotesRepository countryVotesRepository, CandidateService candidateService) {
        this.countryVotesRepository = countryVotesRepository;
        this.candidateService = candidateService;
    }

    public void addCountryVote(CountryVotes countryVotes) {
        this.countryVotesRepository.save(countryVotes);
    }



    public CountryVotes findCountryVoteByShortCode(String shortCode) {
        Candidate candidate = candidateService.findFirstByShortCode(shortCode);
        if (candidate == null) {
            return null;
        }
        return this.countryVotesRepository.findByCandidate(candidate);
    }

    public List<CandidateVotesModel> getAllCountryVotes() {
        List<CountryVotes> countryVotes = countryVotesRepository.findAll();

        return countryVotes.stream().map(vote -> new CandidateVotesModel(vote)).toList();
    }

    public CandidateVotesModel getCountryVotesByCandidate(Candidate candidate) {
        return new CandidateVotesModel(countryVotesRepository.getByCandidateCandidateId(candidate.getCandidateId()));
    }
}
