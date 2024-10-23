package com.example.backend.services;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import com.example.backend.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private static final int MAX_SHORTCODE_LENGTH = 14;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public void addCandidate(Candidate candidate, Affiliation affiliation) {
        Candidate candidateExists = candidateRepository.findByMemberIdAndAffiliationAndConstituencyId(candidate.getMemberId(), affiliation, candidate.getConstituencyId());
        if (candidateExists != null) {
            if (candidateExists.equals(candidate)) {
                return;
            }
        }
        candidateRepository.save(candidate);
    }

    public Candidate getFirstByShortCode(String shortCode) {
        return candidateRepository.getFirstByShortCode((shortCode));
    }

    public String toShortCode(Candidate candidate) {
        String initials = candidate.getInitials().replace(".", "");
        String replacedLastName = candidate.getLastName().replaceAll("[.-]| ", "");
        String shortCode = replacedLastName + initials;

        // if the total length is over 14, reduce the last name's length by the excess amount
        if (initials.length() + replacedLastName.length() > MAX_SHORTCODE_LENGTH) {
            int excessLength = (initials.length() + replacedLastName.length()) - MAX_SHORTCODE_LENGTH;

            shortCode = replacedLastName.substring(0, replacedLastName.length() - excessLength) + initials;
        }

        // Adds a 1 if shortcode already exists
        Candidate shortCodeCandidate = findFirstByShortCode(shortCode);
        if (shortCodeCandidate != null && !shortCodeCandidate.equals(candidate)) {
            return shortCode + "1";
        }

        return shortCode;
    }

    public Candidate findFirstByShortCode(String shortCode) {
        return candidateRepository.findFirstByShortCode(shortCode);
    }
}
