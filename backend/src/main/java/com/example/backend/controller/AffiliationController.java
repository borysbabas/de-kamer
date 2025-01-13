package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Affiliation;
import com.example.backend.models.AffiliationNameModel;
import com.example.backend.models.MemberCountModel;
import com.example.backend.services.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.DTO.AffiliationDTO;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequestMapping(path = ApiPaths.AFFILIATION_SERVICE)
public class AffiliationController {
    private final AffiliationService affiliationService;

    @Autowired
    public AffiliationController(AffiliationService affiliationService) {
        this.affiliationService = affiliationService;
    }

    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_MEMBER_COUNT)
    public List<MemberCountModel> getAllMemberCount() {
        return affiliationService.getMemberCount();
    }
    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_GETBYNAME)
    public AffiliationNameModel getAffiliationByName(@PathVariable String name) {
        Affiliation affiliation = affiliationService.findFirstByAffiliationNameStartingWith(name);
        return new AffiliationNameModel(affiliation.getAffiliationName(), affiliation.getCandidates().get(0).getFullName());
    }
  @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_ALL)
    public List<AffiliationDTO> getAllAffiliations() {
        return affiliationService.getAllAffiliations()
                .stream()
                .map(AffiliationDTO::new)
                .collect(Collectors.toList());
    }
}
