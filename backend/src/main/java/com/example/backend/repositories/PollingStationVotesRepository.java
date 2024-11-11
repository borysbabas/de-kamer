package com.example.backend.repositories;

import com.example.backend.entities.PollingStationVotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollingStationVotesRepository extends JpaRepository<PollingStationVotes, Long> {
}
