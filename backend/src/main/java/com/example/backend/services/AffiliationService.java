package com.example.backend.services;

import com.example.backend.entities.Affiliation;
import com.example.backend.repositories.AffiliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffiliationService {
    private final AffiliationRepository affiliationRepository;

    @Autowired
    public AffiliationService(AffiliationRepository affiliationRepository) {
        this.affiliationRepository = affiliationRepository;
    }

    public Affiliation affiliationExists(Long affiliationIdentifier) {
        return affiliationRepository.findAffiliationByAffiliationIdentifier(affiliationIdentifier);
    }

    public Affiliation addAffiliation(Affiliation affiliation) {
        affiliationRepository.save(affiliation);
        return affiliation;
    }
}
