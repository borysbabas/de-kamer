package com.example.backend.services;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import com.example.backend.repositories.CandidateRepository;
import com.example.backend.utils.TwoKeyHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private static final int MAX_SHORTCODE_LENGTH = 14;
    private final TwoKeyHashMap<Long, Long, Candidate> candidateTwoKeyCache = new TwoKeyHashMap<>();
    private final HashMap<String, Candidate> candidateShortCodeCache = new HashMap<>();

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public void addCandidate(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public Candidate findFirstByShortCode(String shortCode) {
        Candidate candidate = candidateShortCodeCache.get(shortCode);
        if (candidate == null) {
            candidate = candidateRepository.findFirstByShortCode(shortCode);
            candidateShortCodeCache.put(shortCode, candidate);
        }
        return candidate;
    }

    public String toShortCode(Candidate candidate) {
        String initials = candidate.getInitials().replace(".", "");
        String shortCode = getShortCode(candidate, initials);

        // Adds a 1 if shortcode already exists
        Candidate shortCodeCandidate = findFirstByShortCode(shortCode);
        if (shortCodeCandidate != null && !shortCodeCandidate.equals(candidate)) {
            return shortCode + "1";
        }

        return shortCode;
    }

    private static String getShortCode(Candidate candidate, String initials) {
        String replacedLastName = candidate.getLastName().replaceAll("[.-]| |'", "");
        String shortCode = replacedLastName + initials;

        // if the total length is over 14, reduce the last name's length by the excess amount
        if (initials.length() + replacedLastName.length() > MAX_SHORTCODE_LENGTH) {
            int excessLength = (initials.length() + replacedLastName.length()) - MAX_SHORTCODE_LENGTH;

            shortCode = replacedLastName.substring(0, replacedLastName.length() - excessLength) + initials;
        }
        return shortCode;
    }

    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateByCandidateId(Long id){
        return candidateRepository.getCandidateByCandidateId(id);
    }

    public Candidate getCandidateByMemberIdAndAffiliation(Long id, Affiliation affiliation) {
        Candidate candidate = candidateTwoKeyCache.get(id, affiliation.getAffiliationId());
        if (candidate == null) {
            candidate = candidateRepository.getFirstCandidateByMemberIdAndAffiliation(id, affiliation);
            candidateTwoKeyCache.put(id, affiliation.getAffiliationId(), candidate);
        }
        return candidate;
    }

    public Long countDistinctCandidatesByAffiliation(Affiliation affiliation) {
        return candidateRepository.countDistinctCandidatesByAffiliation(affiliation);
    }

    public List<Candidate> getCandidatesByAffiliationName(String affiliationName) {
        return candidateRepository.getCandidatesByAffiliationAffiliationName(affiliationName);
    }
    public List<Candidate> findCandidatesByAffiliation(Affiliation affiliation) {
        return candidateRepository.findCandidatesByAffiliation(affiliation);
    }
}
