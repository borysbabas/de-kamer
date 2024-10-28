package com.example.backend.repositories;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    Candidate findByMemberIdAndAffiliationAndConstituencyId(Long memberId, Affiliation affiliation, Long contestId);
    Candidate getFirstByShortCode(String shortCode);
    Candidate findFirstByShortCode(String shortCode);
    Candidate findByShortCodeAndConstituencyId(String shortCode, Long constituencyId);
}
