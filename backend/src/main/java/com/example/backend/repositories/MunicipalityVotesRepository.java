package com.example.backend.repositories;

import com.example.backend.entities.MunicipalityVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityVotesRepository extends JpaRepository<MunicipalityVotes, Long> {
    MunicipalityVotes getMunicipalityVotesByAffiliation_AffiliationId(Long id);
}
