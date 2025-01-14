package com.example.backend.controller;

import com.example.backend.DTO.MunicipalityDTO;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Constituency;
import com.example.backend.entities.Municipality;
import com.example.backend.models.MunicipalityModel;
import com.example.backend.services.ConstituencyService;
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
    private final ConstituencyService constituencyService;

    @Autowired
    public MunicipalityController(MunicipalityService municipalityService, ConstituencyService constituencyService) {
        this.municipalityService = municipalityService;
        this.constituencyService = constituencyService;
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_ALL)
    public List<MunicipalityModel> getMunicipalities() {
        List<MunicipalityModel> municipalityModels = new ArrayList<>();
        this.municipalityService.getAllMunicipalities().forEach(municipality -> municipalityModels.add(new MunicipalityModel(municipality)));
        return municipalityModels;
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_ID)
    public MunicipalityModel getMunicipalityById(@PathVariable long id) {
        Municipality municipality = this.municipalityService.getMunicipalityByMunicipalityId(id);
        if (municipality == null) {
            return null;
        }
        return new MunicipalityModel(municipality);
    }

    @GetMapping(path = ApiPaths.MUNICIPALITY_SERVICE_BY_CONSTITUENCY)
    public List<MunicipalityDTO> getMunicipalitiesByConstituency(@PathVariable Long constituencyId) {
        Constituency constituency = constituencyService.findConstituencyById(constituencyId);
        List<MunicipalityDTO> municipalityList = new ArrayList<>();
        List<Municipality> municipalities = municipalityService.getMunicipalitiesByConstituency(constituency);

        for (Municipality municipality : municipalities) {
            municipalityList.add(new MunicipalityDTO(
                    municipality.getMunicipalityName(),
                    municipality.getMunicipalityId(),
                    municipality.getConstituency().getConstituencyId()
            ));
        }

        return municipalityList;
    }
}
