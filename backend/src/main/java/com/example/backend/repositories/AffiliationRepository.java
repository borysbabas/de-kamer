package com.example.backend.repositories;

import com.example.backend.entities.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    Affiliation findAffiliationByAffiliationId(Long id);
    Affiliation findFirstByAffiliationNameStartingWithIgnoreCase(String name);
}
