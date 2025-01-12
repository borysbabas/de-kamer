package com.example.backend.repositories;

import com.example.backend.entities.PollingStation;
import com.example.backend.entities.PollingStationVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PollingStationVotesRepository extends JpaRepository<PollingStationVotes, Long> {
    /**
     * Custom query method to retrieve the number of candidates with at least 1 vote in a given polling station.
     * Contains subquery to not take duplicate candidates in the database into account
     *
     * @param pollingStation The polling station you want to get the number of candidates of
     * @return number of candidates with at least 1 vote in the given polling station
     */
    @Query("SELECT COUNT(psv) " +
            "FROM PollingStationVotes psv " +
            "WHERE psv.pollingStation = :pollingStation " +
            "AND psv.id IN (SELECT MIN(subPsv.id) FROM PollingStationVotes subPsv WHERE subPsv.pollingStation = :pollingStation GROUP BY subPsv.candidate)"
    )
    int countByPollingStation(@Param("pollingStation") PollingStation pollingStation);

    /**
     * Custom query method to get a limited number of polling station votes from the database because the required clauses LIMIT and OFFSET are not supported by JPA.
     * Contains a subquery to prevent duplicate candidates in the database being retrieved
     *
     * @param pollingStation The polling station you want to get the votes data of
     * @param pagination The amount of rows of votes data you want to get from the database
     * @param offset The row in the database table from which you want to start getting the data from, best used like "(page - 1) * pagination"
     * @return List of limited number of unique polling station votes from anywhere in the database table
     */
    @Query("SELECT psv " +
            "FROM PollingStationVotes psv " +
            "WHERE psv.pollingStation = :pollingStation " +
            "AND psv.id IN (SELECT MIN(subPsv.id) FROM PollingStationVotes subPsv WHERE subPsv.pollingStation = :pollingStation GROUP BY subPsv.candidate)" +
            "ORDER BY psv.totalVotes DESC " +
            "LIMIT :pagination " +
            "OFFSET :offset")
    List<PollingStationVotes> findByPollingStationByPageOrderByTotalVotesDesc(
            @Param("pollingStation") PollingStation pollingStation,
            @Param("pagination") int pagination,
            @Param("offset") int offset
    );
}
