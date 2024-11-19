package com.example.backend.repositories;

import com.example.backend.entities.Municipality;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    @Query("SELECT m FROM Municipality m ORDER BY m.municipalityName")
    List<Municipality> findAllAndSort();
    Municipality getMunicipalitiesByMunicipalityId(Long municipalityId);
}
