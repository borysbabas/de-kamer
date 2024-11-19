package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Constituency;
import com.example.backend.services.ConstituencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConstituencyController {
    private final ConstituencyService constituencyService;

    @Autowired
    public ConstituencyController(ConstituencyService constituencyService) {
        this.constituencyService = constituencyService;
    }

    @GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE)
    public List<Constituency> getAllConstituencies() {
        return constituencyService.findAllConstituencies();
    }
}
