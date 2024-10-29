package com.example.backend.services;

import com.example.backend.entities.Constituency;
import com.example.backend.repositories.ConstituencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstituencyService {
    private final ConstituencyRepository constituencyRepository;

    @Autowired
    public ConstituencyService(ConstituencyRepository constituencyRepository) {
        this.constituencyRepository = constituencyRepository;
    }

    public void addConstituency(Constituency constituency) {
        constituencyRepository.save(constituency);
    }

    public Constituency setGeneralConstituency() {
        return constituencyRepository.save(new Constituency(
                0L,
                "alle"
        ));
    }

    public Constituency findConstituencyById(Long constituencyId) {
        return constituencyRepository.findById(constituencyId).orElse(null);
    }
}
