package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstituencyModel {
    private String ConstituencyName;
    private Long ConstituencyId;

    public ConstituencyModel(String ConstituencyName, Long ConstituencyId) {
        this.ConstituencyName = ConstituencyName;
        this.ConstituencyId = ConstituencyId;
    }
}
