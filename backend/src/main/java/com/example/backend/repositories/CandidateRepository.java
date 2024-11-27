package com.example.backend.repositories;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Candidate findFirstByShortCode(String shortCode);
    Candidate getFirstCandidateByMemberIdAndAffiliation(Long memberId, Affiliation affiliation);
    Candidate getCandidateByCandidateId(Long id);
    List<Candidate> getCandidatesByAffiliationAffiliationName(String affiliationName);

    @Query("SELECT COUNT(DISTINCT c.shortCode) FROM Candidate c WHERE c.affiliation = ?1")
    Long countDistinctCandidatesByAffiliation(Affiliation affiliation);
}
