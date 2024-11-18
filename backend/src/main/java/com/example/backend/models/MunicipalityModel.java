package com.example.backend.models;

import com.example.backend.entities.Municipality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipalityModel {
    private long id;
    private String name;
    private long constituencyId;

    public MunicipalityModel(Municipality municipality) {
        this.id = municipality.getMunicipalityId();
        this.name = municipality.getMunicipalityName();
        this.constituencyId = municipality.getConstituency().getConstituencyId();
    }
}
