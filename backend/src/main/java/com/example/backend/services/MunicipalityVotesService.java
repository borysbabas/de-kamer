package com.example.backend.services;

import com.example.backend.entities.MunicipalityVotes;
import com.example.backend.models.MunicipalityVoteModel;
import com.example.backend.repositories.MunicipalityVotesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class MunicipalityVotesService {
    private final MunicipalityVotesRepository municipalityVotesRepository;

    @Autowired
    public MunicipalityVotesService(MunicipalityVotesRepository municipalityVotesRepository) {
        this.municipalityVotesRepository = municipalityVotesRepository;
    }

    public void addMunicipalityVote(MunicipalityVotes municipalityVotes) {
        this.municipalityVotesRepository.save(municipalityVotes);
    }
    public List<MunicipalityVoteModel> getTop5MunicipalitiesByCandidateId(Long candidateId){
        return municipalityVotesRepository.findTop5CandidateId(candidateId);
    }
}
