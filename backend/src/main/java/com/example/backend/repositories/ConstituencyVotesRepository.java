package com.example.backend.repositories;

import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstituencyVotesRepository extends JpaRepository<ConstituencyVotes, Long> {
    List<ConstituencyVotes> findByConstituencyOrderByTotalVotesDesc(Constituency constituency);
    int countByConstituency(Constituency constituency);
}
