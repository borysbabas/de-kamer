package com.example.backend.models;

import com.example.backend.entities.Affiliation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCountModel {
    private String affiliationName;
    private Long memberCount;
    private Long affiliationId;


    public MemberCountModel() {
    }

    public MemberCountModel(Affiliation affiliation, Long memberCount) {
        this.affiliationName = affiliation.getAffiliationName();
        this.affiliationId = affiliation.getAffiliationId();
        this.memberCount = memberCount;
    }
}
