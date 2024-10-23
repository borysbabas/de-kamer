package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Affiliation;
import com.example.backend.services.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.backend.models.MemberCount;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = ApiPaths.AFFILIATION_SERVICE)
public class AffiliationController {
    private final AffiliationService affiliationService;

    @Autowired
    public AffiliationController(AffiliationService affiliationService) {
        this.affiliationService = affiliationService;
    }

//    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_MEMBER_COUNT)
//    public List<MemberCount> getAllMemberCount() {
//        List<MemberCount> memberCounts = new ArrayList<>();
//        List<Affiliation> affiliations = affiliationService.getAllAffiliations();
//
//		for (Affiliation affiliation : affiliations) {
//            MemberCount memberCount = new MemberCount();
//			memberCount.setAffiliationName(affiliation.getAffiliationName());
//            memberCount.setMemberCount((long) affiliation.getCandidates().size());
//
//			memberCounts.add(memberCount);
//		}
//        return memberCounts;
//    }
}
