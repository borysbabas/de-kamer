package com.example.backend.repositories;

import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConstituencyVotesRepository extends JpaRepository<ConstituencyVotes, Long> {
    int countByConstituency(Constituency constituency);

    /**
     * Custom query method because the required clauses LIMIT and OFFSET are not supported by JPA
     *
     * @param constituency The constituency you want to get the votes data of
     * @param pagination The amount of rows of votes data you want to get from the database
     * @param offset The row in the database table from which you want to start getting the data from, best used like "(page - 1) * pagination"
     */
    @Query("SELECT cv FROM ConstituencyVotes cv WHERE cv.constituency = :constituency ORDER BY cv.totalVotes DESC LIMIT :pagination OFFSET :offset")
    List<ConstituencyVotes> findByConstituencyByPageOrderByTotalVotesDesc(@Param("constituency") Constituency constituency, @Param("pagination") int pagination, @Param("offset") int offset);
}
