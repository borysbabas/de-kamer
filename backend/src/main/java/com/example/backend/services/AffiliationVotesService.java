package com.example.backend.services;

import com.example.backend.entities.AffiliationVotes;
import com.example.backend.repositories.AffiliationVotesRepository;
import org.springframework.stereotype.Service;

@Service
public class AffiliationVotesService {
    private final AffiliationVotesRepository affiliationVotesRepository;

    public AffiliationVotesService(AffiliationVotesRepository affiliationVotesRepository) {
        this.affiliationVotesRepository = affiliationVotesRepository;
    }

    public void addVote(AffiliationVotes affiliationVotes){
        this.affiliationVotesRepository.save(affiliationVotes);
    }

}
