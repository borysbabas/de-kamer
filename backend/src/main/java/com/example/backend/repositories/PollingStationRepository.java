package com.example.backend.repositories;

import com.example.backend.entities.Municipality;
import com.example.backend.entities.PollingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollingStationRepository extends JpaRepository<PollingStation, Long> {
    List<PollingStation> getPollingStationsByMunicipality(Municipality municipality);

    PollingStation getPollingStationById(Long id);
}
