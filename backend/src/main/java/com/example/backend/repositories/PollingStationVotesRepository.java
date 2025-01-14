package com.example.backend.repositories;

import com.example.backend.entities.PollingStation;
import com.example.backend.entities.PollingStationVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PollingStationVotesRepository extends JpaRepository<PollingStationVotes, Long> {
    @Query("SELECT COUNT(psv) " +
            "FROM PollingStationVotes psv " +
            "WHERE psv.pollingStation = :pollingStation " +
            "AND psv.id IN (SELECT MIN(subPsv.id) FROM PollingStationVotes subPsv WHERE subPsv.pollingStation = :pollingStation GROUP BY subPsv.candidate)"
    )
    int countByPollingStation(@Param("pollingStation") PollingStation pollingStation);

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
