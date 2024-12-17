package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Constituency;
import com.example.backend.DTO.ConstituencyDTO;
import com.example.backend.services.ConstituencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConstituencyController {
    private final ConstituencyService constituencyService;

    @Autowired
    public ConstituencyController(ConstituencyService constituencyService) {
        this.constituencyService = constituencyService;
    }

    @GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE)
    public List<ConstituencyDTO> getAllConstituencies() {
        List<Constituency> constituencies = constituencyService.findAllConstituencies();
        List<ConstituencyDTO> constituencyList = new ArrayList<>();

        for (Constituency constituency : constituencies) {
            // Exclude constituencies "alle" and "Bonaire", because they have little to no data elsewhere in the database
            if (!constituency.getConstituencyName().equals("alle") && !constituency.getConstituencyName().equals("Bonaire"))
                constituencyList.add(new ConstituencyDTO(
                        constituency.getConstituencyName(),
                        constituency.getConstituencyId()
                ));
        }
        return constituencyList;
    }
}
