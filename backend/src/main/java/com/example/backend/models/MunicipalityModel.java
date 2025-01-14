package com.example.backend.models;

import com.example.backend.entities.Municipality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipalityModel {
    private long municipalityId;
    private String name;
    private long constituencyId;

    // Constructor needed for tests
    public MunicipalityModel(){}

    public MunicipalityModel(long municipalityId, String name, long constituencyId) {
        this.municipalityId = municipalityId;
        this.name = name;
        this.constituencyId = constituencyId;
    }

    public MunicipalityModel(Municipality municipality) {
        this.municipalityId = municipality.getMunicipalityId();
        this.name = municipality.getMunicipalityName();
        this.constituencyId = municipality.getConstituency().getConstituencyId();
    }
}
