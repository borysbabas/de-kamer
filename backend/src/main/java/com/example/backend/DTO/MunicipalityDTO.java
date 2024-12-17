package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipalityDTO {
    private String municipalityName;
    private Long municipalityId;
    private Long constituencyId;

    public MunicipalityDTO(String municipalityName, Long municipalityId, Long constituencyId) {
        this.municipalityName = municipalityName;
        this.municipalityId = municipalityId;
        this.constituencyId = constituencyId;
    }
}
