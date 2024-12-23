package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.*;
import com.example.backend.models.*;
import com.example.backend.services.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

// Controller for made for leaflet data (autofix dataformat)
//*
// Dataformat:
// {
//     "data": [
//         {
//             name: "location name"
//             lat: "latitude",
//             lon: "longitude"
//             value: "value"
//             id: "id"
//         },


@RestController
@RequestMapping(path = ApiPaths.LEAFLET_SERVICE)
public class LeaflatController {


    private final MunicipalityService municipalityService;
    private final MunicipalityVotesService municipalityVotesService;

    @Autowired
    public LeaflatController(
            MunicipalityService municipalityService,
            MunicipalityVotesService municipalityVotesService

    ) {
        this.municipalityService = municipalityService;
        this.municipalityVotesService = municipalityVotesService;
    }


    @GetMapping(path = ApiPaths.MUNICIPALITY_SERVICE_GET_MUNICIPALITYVOTES_BY_AFFILIATIONID)
    public Map<String, Object> getVotesByMunicipality(@PathVariable Long affiliationId) {
        List<MunicipalityVoteModel> municipalityVotesList = municipalityVotesService.findTotalVotesByAffiliationId(affiliationId);
        List<Map<String, Object>> data = new ArrayList<>();

        int idCounter = 1;
        for (MunicipalityVoteModel municipalityVotes : municipalityVotesList) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", municipalityVotes.getMunicipalityName());
            map.put("value", municipalityVotes.getTotalVotes());
            map.put("id", municipalityVotes.getId());


            // Exteranl API data
            map.put("lat", null);
            map.put("lon", null);

            data.add(map);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        return response;
    }

}
