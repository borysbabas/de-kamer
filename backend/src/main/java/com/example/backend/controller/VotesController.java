package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/votes")
public class VotesController {
    @GetMapping(path = "/total/affiliation")
    public void getTotalAffiliationVotes() {

    }
}
