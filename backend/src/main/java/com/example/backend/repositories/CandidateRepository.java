package com.example.backend.repositories;

import com.example.backend.entities.Candidate;
import com.example.backend.entities.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    Candidate findByMemberIdAndParty(Long memberId, Party party);
}
