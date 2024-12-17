package com.example.backend.repositories;

import com.example.backend.entities.Constituency;
import com.example.backend.entities.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    List<Municipality> findAllByOrderByMunicipalityNameAsc();
    Municipality getMunicipalitiesByMunicipalityId(Long municipalityId);

    List<Municipality> getMunicipalitiesByConstituency(Constituency constituency);
}
