package com.example.backend.repositories;

import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConstituencyVotesRepository extends JpaRepository<ConstituencyVotes, Long> {
    int countByConstituency(Constituency constituency);

    @Query("SELECT cv FROM ConstituencyVotes cv WHERE cv.constituency = :constituency ORDER BY cv.totalVotes DESC LIMIT :pagination OFFSET :offset")
    List<ConstituencyVotes> findByConstituencyByPageOrderByTotalVotesDesc(@Param("constituency") Constituency constituency, @Param("pagination") int pagination, @Param("offset") int offset);
}
