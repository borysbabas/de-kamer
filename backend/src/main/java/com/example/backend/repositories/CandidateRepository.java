package com.example.backend.repositories;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.Candidate;
import com.example.backend.models.CandidateVotesModel;
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



    long countDistinctCandidatesByAffiliation(Affiliation affiliation);
    List<Candidate> findCandidatesByAffiliation(Affiliation affiliation);
}
