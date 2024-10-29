package com.example.backend.services;

import com.example.backend.entities.Municipality;
import com.example.backend.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunicipalityService {
    private final MunicipalityRepository municipalityRepository;

    @Autowired
    public MunicipalityService(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }

    public void addMunicipality(Municipality municipality) {
        municipalityRepository.save(municipality);
    }
}
