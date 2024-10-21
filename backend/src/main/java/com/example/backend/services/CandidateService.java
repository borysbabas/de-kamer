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
        Candidate candidateExists = candidateRepository.findByMemberIdAndAffiliation(candidate.getMemberId(), affiliation);
        if (candidateExists != null) {
            if (candidateExists.equals(candidate)) {
                return;
            }
            System.out.println("Candidate added: " + candidateExists.equals(candidate) + " " + candidateExists.getCandidateId() + " " + candidateExists.getAffiliation().getAffiliationName() + " " + candidate.getMemberId());
            return;
        }
        candidateRepository.save(candidate);
    }
}
