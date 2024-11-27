package com.example.backend.repositories;

import com.example.backend.entities.Municipality;
import com.example.backend.entities.MunicipalityVotes;
import com.example.backend.models.MunicipalityVoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MunicipalityVotesRepository extends JpaRepository<MunicipalityVotes, Long> {
    List<MunicipalityVotes> findByMunicipalityIn(Collection<Municipality> municipalities);
    @Query("SELECT DISTINCT new com.example.backend.models.MunicipalityVoteModel(m.municipalityName, mv.totalVotes) "+"FROM MunicipalityVotes mv "+"JOIN mv.municipality m "+ "WHERE mv.candidate.candidateId = :candidateId "+"ORDER BY mv.totalVotes DESC")
    List<MunicipalityVoteModel> findTop5CandidateId(@Param("candidateId")Long candidateId);
}
