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

    @Query("SELECT new com.example.backend.models.MunicipalityVoteModel(mv.municipality.municipalityName, SUM(mv.totalVotes), mv.municipality.municipalityId) " +
            "FROM MunicipalityVotes mv WHERE mv.affiliation.affiliationId = :affiliationId GROUP BY mv.municipality.municipalityName")
    List<MunicipalityVoteModel> findTotalVotesByAffiliationId(@Param("affiliationId") long affiliationId);

    /**
     * Custom query method to retrieve the number of candidates with at least 1 vote in a given municipality.
     * Contains subquery to not take duplicate candidates in the database into account
     *
     * @param municipality The municipality you want to get the number of candidates of
     * @return number of candidates with at least 1 vote in the given municipality
     */
    @Query("SELECT COUNT(mv) " +
            "FROM MunicipalityVotes mv " +
            "WHERE mv.municipality = :municipality " +
            "AND mv.id IN (SELECT MIN(subMv.id) FROM MunicipalityVotes subMv WHERE subMv.municipality = :municipality GROUP BY subMv.candidate)"
    )
    int countMunicipalityVotesByMunicipality(@Param("municipality") Municipality municipality);

    /**
     * Custom query method to get a limited number of municipality votes from the database because the required clauses LIMIT and OFFSET are not supported by JPA.
     * Contains a subquery to prevent duplicate candidates in the database being retrieved
     *
     * @param municipality The municipality you want to get the votes data of
     * @param pagination The amount of rows of votes data you want to get from the database
     * @param offset The row in the database table from which you want to start getting the data from, best used like "(page - 1) * pagination"
     * @return List of limited number of unique municipality votes from anywhere in the database table
     */
    @Query("SELECT mv " +
            "FROM MunicipalityVotes mv " +
            "WHERE mv.municipality = :municipality " +
            "AND mv.id IN (SELECT MIN(subMv.id) FROM MunicipalityVotes subMv WHERE subMv.municipality = :municipality GROUP BY subMv.candidate)" +
            "ORDER BY mv.totalVotes DESC " +
            "LIMIT :pagination " +
            "OFFSET :offset")
    List<MunicipalityVotes> findByMunicipalityByPageOrderByTotalVotesDesc(@Param("municipality") Municipality municipality, @Param("pagination") int pagination, @Param("offset") int offset);
}
