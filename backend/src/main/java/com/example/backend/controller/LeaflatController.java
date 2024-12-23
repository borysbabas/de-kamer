package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.*;
import com.example.backend.models.*;
import com.example.backend.services.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

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
        List<Long> municipalityIds = new ArrayList<>();
        for (MunicipalityVoteModel municipalityVotes : municipalityVotesList) {
            municipalityIds.add(municipalityVotes.getId());
        }
        Map<String, Map<String, Object>> cords = fetchCords();
        System.out.println(cords + " cords");


        for (MunicipalityVoteModel municipalityVotes : municipalityVotesList) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", municipalityVotes.getMunicipalityName());
            map.put("value", municipalityVotes.getTotalVotes());
            map.put("id", municipalityVotes.getId());

            String formattedId = String.format("%04d", municipalityVotes.getId());
            Map<String, Object> cord = cords.get(formattedId);
            if (cord != null) {
                map.put("lat", cord.get("lat"));
                map.put("lon", cord.get("lon"));
            } else {
                map.put("lat", null);
                map.put("lon", null);
            }

            data.add(map);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        return response;
    }

    private Map<String, Map<String, Object>> fetchCords() {
        Map<String, Map<String, Object>> cords = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        for (int offset = 0; offset < 400; offset += 100) {
            String url = "https://public.opendatasoft.com/api/explore/v2.1/catalog/datasets/georef-netherlands-gemeente/records?select=geo_point_2d,gem_name,gem_code&limit=100&offset=" + offset;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("results");
                if (data != null) {
                    data.forEach((item) -> {
                        Map<String, Object> cord = new HashMap<>();
                        Map<String, Object> geoPoint = (Map<String, Object>) item.get("geo_point_2d");
                        if (geoPoint != null) {
                            cord.put("lat", geoPoint.get("lat"));
                            cord.put("lon", geoPoint.get("lon"));
                        }
                        cord.put("name", ((List<String>) item.get("gem_name")).get(0));
                        cord.put("id", ((List<String>) item.get("gem_code")).get(0));
                        cords.put(((List<String>) item.get("gem_code")).get(0), cord);
                    });
                }
            }
        }
        System.out.println(cords);
        return cords;
    }
}
