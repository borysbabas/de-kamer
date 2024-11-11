package com.example.backend.services;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.AffiliationVotes;
import com.example.backend.repositories.AffiliationVotesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffiliationVotesService {
    private final AffiliationVotesRepository affiliationVotesRepository;
    private final AffiliationService affiliationService;

    public AffiliationVotesService(AffiliationVotesRepository affiliationVotesRepository, AffiliationService affiliationService) {
        this.affiliationVotesRepository = affiliationVotesRepository;
        this.affiliationService = affiliationService;
    }

    public void addVote(AffiliationVotes affiliationVotes){
        this.affiliationVotesRepository.save(affiliationVotes);
    }

    public List<AffiliationVotes> findAllAffiliationVotes() {
        return affiliationVotesRepository.findAll();
    }

    public AffiliationVotes findByAffiliationId(long affiliationId) {
        Affiliation affiliation = affiliationService.findAffiliationByAffiliationId(affiliationId);
        if (affiliation == null) {
            return null;
        }
        return affiliationVotesRepository.findByAffiliation(affiliation);
    }
}
