package com.example.backend.services;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import com.example.backend.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;

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

    public String toShortCode(Candidate candidate) {
        return (candidate.getLastName() + candidate.getInitials()).replaceAll("[.-]", "");
    }
}
