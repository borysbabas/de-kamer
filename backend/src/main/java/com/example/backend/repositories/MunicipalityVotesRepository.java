package com.example.backend.repositories;

import com.example.backend.entities.Municipality;
import com.example.backend.entities.MunicipalityVotes;
import com.example.backend.models.MunicipalityAffiliationVotesModel;
import com.example.backend.models.MunicipalityVoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipalityVotesRepository extends JpaRepository<MunicipalityVotes, Long> {
    List<MunicipalityVotes> findByMunicipalityIn(Collection<Municipality> municipalities);
    @Query("SELECT DISTINCT new com.example.backend.models.MunicipalityVoteModel(m.municipalityName, mv.totalVotes) "+"FROM MunicipalityVotes mv "+"JOIN mv.municipality m "+ "WHERE mv.candidate.candidateId = :candidateId "+"ORDER BY mv.totalVotes DESC")
    List<MunicipalityVoteModel> findTop5CandidateId(@Param("candidateId")Long candidateId);

    // SUM and ORDER BY cannot be used in the JPA query, so we need to use native query
    @Query("SELECT new com.example.backend.models.MunicipalityAffiliationVotesModel(" +
            "mv.municipality, mv.affiliation, SUM(mv.totalVotes), mv.municipality.municipalityId) " +
            "FROM MunicipalityVotes mv " +
            "WHERE mv.municipality.municipalityId=?1 " +
            "GROUP BY mv.affiliation.affiliationId " +
            "ORDER BY SUM(mv.totalVotes) DESC")
    List<MunicipalityAffiliationVotesModel> getMunicipalityAffiliationVotes(long municipalityId);

    Optional<MunicipalityVotes> findTopByMunicipalityMunicipalityIdOrderByTotalVotesDesc(Long municipalityId);

    // GROUP BY cannot be used in the JPA query, so we need to use native query
    @Query("SELECT new com.example.backend.models.MunicipalityAffiliationVotesModel(" +
            "mv.municipality, mv.affiliation, SUM(mv.totalVotes), mv.municipality.municipalityId)" +
            "FROM MunicipalityVotes mv " +
            "WHERE mv.affiliation.affiliationId = :affiliationId " +
            "GROUP BY mv.municipality.municipalityId")
    List<MunicipalityAffiliationVotesModel> findAllByAffiliationAffiliationId(long affiliationId);

    @Query("SELECT COUNT(mv) " +
            "FROM MunicipalityVotes mv " +
            "WHERE mv.municipality = :municipality " +
            "AND mv.id IN (SELECT MIN(subMv.id) FROM MunicipalityVotes subMv WHERE subMv.municipality = :municipality GROUP BY subMv.candidate)"
    )
    int countMunicipalityVotesByMunicipality(@Param("municipality") Municipality municipality);

    @Query("SELECT mv " +
            "FROM MunicipalityVotes mv " +
            "WHERE mv.municipality = :municipality " +
            "AND mv.id IN (SELECT MIN(subMv.id) FROM MunicipalityVotes subMv WHERE subMv.municipality = :municipality GROUP BY subMv.candidate)" +
            "ORDER BY mv.totalVotes DESC " +
            "LIMIT :pagination " +
            "OFFSET :offset")
    List<MunicipalityVotes> findByMunicipalityByPageOrderByTotalVotesDesc(@Param("municipality") Municipality municipality, @Param("pagination") int pagination, @Param("offset") int page);
}
