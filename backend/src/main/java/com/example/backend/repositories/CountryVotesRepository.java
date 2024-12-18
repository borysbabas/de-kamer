package com.example.backend.repositories;

import com.example.backend.entities.Candidate;
import com.example.backend.entities.CountryVotes;
import com.example.backend.models.CandidateVotesModel;
import com.example.backend.models.MemberCountModel;
import com.example.backend.models.MunicipalityVoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryVotesRepository extends JpaRepository<CountryVotes, Long> {
    CountryVotes findByCandidate(Candidate candidate);
    CountryVotes getByCandidateCandidateId(long candidateId);

    @Query("SELECT new com.example.backend.models.MemberCountModel(cv.affiliation, count(cv.candidate))" +
            " FROM CountryVotes cv" +
            " GROUP BY cv.affiliation.affiliationId")
    List<MemberCountModel> getMemberCount();

    List<CountryVotes> findByAffiliationAffiliationId(Long affiliationId);
}
