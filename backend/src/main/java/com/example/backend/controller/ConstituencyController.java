package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Constituency;
import com.example.backend.models.ConstituencyModel;
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
    public List<ConstituencyModel> getAllConstituencies() {
        List<Constituency> constituencies = constituencyService.findAllConstituencies();
        List<ConstituencyModel> constituencyList = new ArrayList<>();
        System.out.println(constituencyList);
        for (Constituency constituency : constituencies) {
            constituencyList.add(new ConstituencyModel(
                    constituency.getConstituencyName(),
                    constituency.getConstituencyId()
            ));
        }
        return constituencyList;
    }
}
