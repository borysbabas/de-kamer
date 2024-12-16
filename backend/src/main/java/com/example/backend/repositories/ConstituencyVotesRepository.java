package com.example.backend.repositories;

import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import com.example.backend.models.ConstituencyVotesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConstituencyVotesRepository extends JpaRepository<ConstituencyVotes, Long> {
    @Query("SELECT COUNT(cv) " +
            "FROM ConstituencyVotes cv " +
            "WHERE cv.constituency = :constituency " +
            "AND cv.id IN (SELECT MIN(subCv.id) FROM ConstituencyVotes subCv WHERE subCv.constituency = :constituency GROUP BY subCv.candidate)"
    )
    int countByConstituency(@Param("constituency") Constituency constituency);

    /**
     * Custom query method to get a limited number of constituency votes from the database because the required clauses LIMIT and OFFSET are not supported by JPA
     *
     * @param constituency The constituency you want to get the votes data of
     * @param pagination The amount of rows of votes data you want to get from the database
     * @param offset The row in the database table from which you want to start getting the data from, best used like "(page - 1) * pagination"
     * @return List of limited number of unique constituency votes from anywhere in the database table
     */
    @Query("SELECT cv " +
            "FROM ConstituencyVotes cv " +
            "WHERE cv.constituency = :constituency " +
            "AND cv.id IN (SELECT MIN(subCv.id) FROM ConstituencyVotes subCv WHERE subCv.constituency = :constituency GROUP BY subCv.candidate)" +
            "ORDER BY cv.totalVotes DESC " +
            "LIMIT :pagination " +
            "OFFSET :offset")
    List<ConstituencyVotes> findByConstituencyByPageOrderByTotalVotesDesc(
            @Param("constituency") Constituency constituency,
            @Param("pagination") int pagination,
            @Param("offset") int offset
    );

    @Query("SELECT new com.example.backend.models.ConstituencyVotesModel(cv.constituency.constituencyName, SUM(cv.totalVotes)) " +
            "FROM ConstituencyVotes cv " +
            "WHERE cv.affiliation.affiliationId = :affiliationId " +
            "GROUP BY cv.constituency.constituencyName")
    List<ConstituencyVotesModel> findTotalVotesByAffiliationGroupedByConstituency(@Param("affiliationId") Long affiliationId);

    @Query("SELECT new com.example.backend.models.ConstituencyVotesModel(cv.constituency.constituencyName, SUM(cv.totalVotes)) " +
            "FROM ConstituencyVotes cv " +
            "WHERE cv.affiliation.affiliationId = :affiliationId " +
            "AND cv.candidate.candidateId = :candidateId " +
            "GROUP BY cv.constituency.constituencyName")
    List<ConstituencyVotesModel> findTotalVotesByAffiliationGroupedByConstituencyAndCandidate(@Param("affiliationId") Long affiliationId, @Param("candidateId") Long candidateId);
}
