package com.example.backend.services;

import com.example.backend.entities.AffiliationVotes;
import com.example.backend.repositories.AffiliationVotesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffiliationVotesService {
    private final AffiliationVotesRepository affiliationVotesRepository;

    public AffiliationVotesService(AffiliationVotesRepository affiliationVotesRepository) {
        this.affiliationVotesRepository = affiliationVotesRepository;
    }

    public void addVote(AffiliationVotes affiliationVotes){
        this.affiliationVotesRepository.save(affiliationVotes);
    }

    public List<AffiliationVotes> findAllAffiliationVotes() {
        return affiliationVotesRepository.findAll();
    }

}
