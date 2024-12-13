package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.models.MunicipalityModel;
import com.example.backend.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MunicipalityController {
    private final MunicipalityService municipalityService;

    @Autowired
    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_ALL)
    public List<MunicipalityModel> getMunicipalities() {
        List<MunicipalityModel> municipalityModels = new ArrayList<>();
        this.municipalityService.getAllMunicipalities().forEach(municipality -> municipalityModels.add(new MunicipalityModel(municipality)));
        return municipalityModels;
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_ID)
    public MunicipalityModel getMunicipalityById(@PathVariable long id) {
        return new MunicipalityModel(this.municipalityService.getMunicipalityByMunicipalityId(id));
    }
}
