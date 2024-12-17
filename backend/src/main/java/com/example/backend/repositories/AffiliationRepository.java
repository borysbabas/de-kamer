package com.example.backend.repositories;

import com.example.backend.entities.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    Affiliation findAffiliationByAffiliationId(Long id);
    Affiliation findFirstByAffiliationNameStartingWithIgnoreCase(String name);
}
