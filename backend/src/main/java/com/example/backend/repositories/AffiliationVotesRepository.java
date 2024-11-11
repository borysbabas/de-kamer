package com.example.backend.repositories;

import com.example.backend.entities.Affiliation;
import com.example.backend.entities.AffiliationVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffiliationVotesRepository extends JpaRepository<AffiliationVotes, Long> {
    AffiliationVotes findByAffiliation(Affiliation affiliation);
}
