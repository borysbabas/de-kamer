package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Affiliation;
import com.example.backend.models.MemberCountModel;
import com.example.backend.services.AffiliationService;
import com.example.backend.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = ApiPaths.AFFILIATION_SERVICE)
public class AffiliationController {
    private final AffiliationService affiliationService;
    private final CandidateService candidateService;

    @Autowired
    public AffiliationController(AffiliationService affiliationService, CandidateService candidateService) {
        this.affiliationService = affiliationService;
        this.candidateService = candidateService;
    }

    @GetMapping(path = ApiPaths.AFFILIATION_SERVICE_MEMBER_COUNT)
    public List<MemberCountModel> getAllMemberCount() {
        List<MemberCountModel> memberCountModels = new ArrayList<>();
        List<Affiliation> affiliations = affiliationService.getAllAffiliations();

		for (Affiliation affiliation : affiliations) {
            MemberCountModel memberCountModel = new MemberCountModel();
			memberCountModel.setAffiliationName(affiliation.getAffiliationName());
            memberCountModel.setMemberCount(
                    candidateService.countDistinctCandidatesByAffiliation(affiliation)
            );

			memberCountModels.add(memberCountModel);
		}
        return memberCountModels;
    }
}
