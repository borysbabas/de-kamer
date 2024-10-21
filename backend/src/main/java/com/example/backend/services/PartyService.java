package com.example.backend.services;

import com.example.backend.entities.Party;
import com.example.backend.repositories.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyService {
    private final PartyRepository partyRepository;

    @Autowired
    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public void addParty(Party party) {
        partyRepository.save(party);
    }
}
