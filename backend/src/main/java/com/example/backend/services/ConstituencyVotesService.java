package com.example.backend.services;

import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
import com.example.backend.models.ConstituencyVotesModel;
import com.example.backend.repositories.ConstituencyVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstituencyVotesService {
    private final ConstituencyVotesRepository constituencyVotesRepository;

    @Autowired
    public ConstituencyVotesService(ConstituencyVotesRepository constituencyVotesRepository) {
        this.constituencyVotesRepository = constituencyVotesRepository;
    }

    public void addConstituencyVotes(ConstituencyVotes constituencyVotes) {
        constituencyVotesRepository.save(constituencyVotes);
    }

    public int getConstituencyVotesCountByConstituency(Constituency constituency) {
        return constituencyVotesRepository.countByConstituency(constituency);
    }

    public List<ConstituencyVotes> getConstituencyVotesByConstituencyByPage(Constituency constituency, int pagination, int page) {
        return constituencyVotesRepository.findByConstituencyByPageOrderByTotalVotesDesc(constituency, pagination, (page - 1) * pagination);
    }
    public List<ConstituencyVotesModel> getConstituencyVotesByAffiliationId(Long affiliationId) {
        return constituencyVotesRepository.findTotalVotesByAffiliationGroupedByConstituency(affiliationId);
    }
    public List<ConstituencyVotesModel> getConstituencyVotesByAffiliationIdAndCandidateId(Long affiliationId, Long candidateId) {
        return constituencyVotesRepository.findTotalVotesByAffiliationGroupedByConstituencyAndCandidate(affiliationId, candidateId);
    }

}
