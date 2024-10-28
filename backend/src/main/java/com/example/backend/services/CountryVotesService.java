package com.example.backend.services;

import com.example.backend.entities.CountryVotes;
import com.example.backend.repositories.CountryVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryVotesService {
    private final CountryVotesRepository countryVotesRepository;

    @Autowired
    public CountryVotesService(CountryVotesRepository countryVotesRepository) {
        this.countryVotesRepository = countryVotesRepository;
    }

    public void addCountryVote(CountryVotes countryVotes) {
        this.countryVotesRepository.save(countryVotes);
    }
}
