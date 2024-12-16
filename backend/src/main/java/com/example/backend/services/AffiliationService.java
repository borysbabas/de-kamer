package com.example.backend.services;

import com.example.backend.entities.Affiliation;
import com.example.backend.models.MemberCountModel;
import com.example.backend.repositories.AffiliationRepository;
import com.example.backend.repositories.CountryVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AffiliationService {
    private final AffiliationRepository affiliationRepository;
    private final HashMap<Long, Affiliation> affiliationCache = new HashMap<>();
    private final CountryVotesRepository countryVotesRepository;

    @Autowired
    public AffiliationService(AffiliationRepository affiliationRepository, CountryVotesRepository countryVotesRepository) {
        this.affiliationRepository = affiliationRepository;
        this.countryVotesRepository = countryVotesRepository;
    }

    public Affiliation findAffiliationByAffiliationId(Long affiliationId) {
        Affiliation affiliation = affiliationCache.get(affiliationId);
        if (affiliation == null) {
            affiliation = affiliationRepository.findAffiliationByAffiliationId(affiliationId);
            affiliationCache.put(affiliationId, affiliation);
        }
        return affiliation;
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

    public List<MemberCountModel> getMemberCount() {
        return countryVotesRepository.getMemberCount();
    }

    public Affiliation findFirstByAffiliationNameStartingWith(String name) {
        return affiliationRepository.findFirstByAffiliationNameStartingWithIgnoreCase(name);
    }
}
