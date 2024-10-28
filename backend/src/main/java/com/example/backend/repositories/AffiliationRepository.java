package com.example.backend.repositories;

import com.example.backend.entities.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    Affiliation findAffiliationByAffiliationId(Long id);
    Affiliation findFirstByAffiliationNameStartingWithIgnoreCase(String name);
    @Query("SELECT a.affiliationName FROM Affiliation a WHERE a.affiliationId = ?1")
    String findAffiliationNameByAffiliationId(Long affiliationId);
}
