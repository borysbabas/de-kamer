package com.example.backend.DTO;

import com.example.backend.entities.Affiliation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AffiliationDTO {
    private Long affiliationId;
    private String affiliationName;

    public AffiliationDTO(Affiliation affiliation) {
        this.affiliationId = affiliation.getAffiliationId();
        this.affiliationName = affiliation.getAffiliationName();
    }
}
