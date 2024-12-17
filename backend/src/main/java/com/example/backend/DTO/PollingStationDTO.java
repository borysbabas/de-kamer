package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PollingStationDTO {
    private String pollingStationName;
    private Long pollingStationId;
    private Long municipalityId;

    public PollingStationDTO(String pollingStationName, Long pollingStationId, Long municipalityId) {
        this.pollingStationName = pollingStationName;
        this.pollingStationId = pollingStationId;
        this.municipalityId = municipalityId;
    }
}
