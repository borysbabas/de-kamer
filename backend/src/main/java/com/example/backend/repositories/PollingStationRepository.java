package com.example.backend.repositories;

import com.example.backend.entities.PollingStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollingStationRepository extends JpaRepository<PollingStation, Long> {
}
