package com.example.backend.services;

import com.example.backend.entities.Constituency;
import com.example.backend.repositories.ConstituencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ConstituencyService {
    private final ConstituencyRepository constituencyRepository;
    private final HashMap<Long, Constituency> constituencyCache = new HashMap<>();

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

    public List<Constituency> findAllConstituencies() {
        return constituencyRepository.findAllByOrderByConstituencyNameAsc();
    }

    public Constituency findConstituencyById(Long constituencyId) {
        return constituencyCache.computeIfAbsent(constituencyId, k -> constituencyRepository.findById(constituencyId).orElse(null));
    }
}
