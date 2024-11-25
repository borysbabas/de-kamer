package com.example.backend.services;

import com.example.backend.entities.Constituency;
import com.example.backend.entities.ConstituencyVotes;
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

    public List<ConstituencyVotes> getConstituencyVotesByConstituency(Constituency constituency) {
        return constituencyVotesRepository.findByConstituencyOrderByTotalVotesDesc(constituency);
    public int getConstituencyVotesCountByConstituency(Constituency constituency) {
        return constituencyVotesRepository.countByConstituency(constituency);
    }
    }

}
