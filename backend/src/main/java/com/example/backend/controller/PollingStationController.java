package com.example.backend.controller;

import com.example.backend.DTO.PollingStationDTO;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Municipality;
import com.example.backend.entities.PollingStation;
import com.example.backend.services.MunicipalityService;
import com.example.backend.services.PollingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PollingStationController {
    private final PollingStationService pollingStationService;
    private final MunicipalityService municipalityService;

    @Autowired
    public PollingStationController(PollingStationService pollingStationService, MunicipalityService municipalityService) {
        this.pollingStationService = pollingStationService;
        this.municipalityService = municipalityService;
    }

    @GetMapping(path = ApiPaths.POLLING_STATION_BY_MUNICIPALITY)
    public List<PollingStationDTO> getPollingStationsByMunicipalityId(@PathVariable("municipalityId") Long municipalityId) {
        Municipality municipality = municipalityService.getMunicipalityByMunicipalityId(municipalityId);
        List<PollingStationDTO> pollingStationList= new ArrayList<>();
        List<PollingStation> pollingStations = pollingStationService.getPollingStationsByMunicipality(municipality);

        for (PollingStation pollingStation : pollingStations) {
            pollingStationList.add(new PollingStationDTO(
                    pollingStation.getPollingStationName(),
                    pollingStation.getId(),
                    pollingStation.getMunicipality().getMunicipalityId()
            ));
        }

        return pollingStationList;
    }
}
