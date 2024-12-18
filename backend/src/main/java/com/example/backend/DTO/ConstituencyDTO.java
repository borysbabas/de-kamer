package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstituencyDTO {
    private String ConstituencyName;
    private Long ConstituencyId;

    public ConstituencyDTO(String ConstituencyName, Long ConstituencyId) {
        this.ConstituencyName = ConstituencyName;
        this.ConstituencyId = ConstituencyId;
    }
}
