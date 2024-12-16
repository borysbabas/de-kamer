package com.example.backend.services;

import com.example.backend.entities.Affiliation;
import com.example.backend.repositories.AffiliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AffiliationService {
    private final AffiliationRepository affiliationRepository;
    private final HashMap<Long, Affiliation> affiliationCache = new HashMap<>();

    @Autowired
    public AffiliationService(AffiliationRepository affiliationRepository) {
        this.affiliationRepository = affiliationRepository;
    }

    public Affiliation findAffiliationByAffiliationId(Long affiliationId) {
        return affiliationCache.computeIfAbsent(affiliationId, k -> affiliationRepository.findAffiliationByAffiliationId(affiliationId));
    }

    public void addAffiliation(Affiliation affiliation) {
        affiliationRepository.save(affiliation);
    }

    public String findAffiliationNameByAffiliationId(Long affiliationId) {
        Affiliation affiliation = findAffiliationByAffiliationId(affiliationId);
        if (affiliation == null) {
            return null;
        }
        return affiliation.getAffiliationName();
    }

    public List<Affiliation> getAllAffiliations() {
        return affiliationRepository.findAll();
    }

    public Affiliation findFirstByAffiliationNameStartingWith(String name) {
        return affiliationRepository.findFirstByAffiliationNameStartingWithIgnoreCase(name);
    }
}
