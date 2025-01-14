package com.example.backend;

import com.example.backend.controller.ConstituencyController;
import com.example.backend.controller.MunicipalityController;
import com.example.backend.controller.PollingStationController;
import com.example.backend.controller.VotesTableController;
import com.example.backend.models.MunicipalityModel;
import com.example.backend.services.ConstituencyService;
import com.example.backend.services.MunicipalityService;
import com.example.backend.services.PollingStationService;
import com.example.backend.services.VotesTableService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("production")
public class MunicipalityTest {
    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final int MUNICIPALITY_AMOUNT = 355;
    private final int MUNICIPALITY_ID = 34;
    private final String MUNICIPALITY_NAME = "Almere";
    private final int CONSTITUENCY_ID = 1;
    private final MunicipalityModel[] MUNICIPALITIES_CONSTITUENCY = {
            new MunicipalityModel(14, "Groningen", 1),
            new MunicipalityModel(37, "Stadskanaal", 1),
            new MunicipalityModel(47, "Veendam", 1),
            new MunicipalityModel(765, "Pekela", 1),
            new MunicipalityModel(1895, "Oldambt", 1),
            new MunicipalityModel(1950, "Westerwolde", 1),
            new MunicipalityModel(1952, "Midden-Groningen", 1),
            new MunicipalityModel(1966, "Het Hogeland", 1),
            new MunicipalityModel(1969, "Westerkwartier", 1),
            new MunicipalityModel(1979, "Eemsdelta", 1)
    };
    @Test
    public void contextLoads() {
        assertThat(municipalityService).isNotNull();
    }

    @Test
    public void shouldReturnCorrectMunicipalityAmount() throws Exception {
        ResultActions result = mockMvc.perform(get("/municipalities"))
                .andExpect(status().isOk());

        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        List<MunicipalityModel> municipalities = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertThat(municipalities).hasSize(MUNICIPALITY_AMOUNT);
    }

    @Test
    public void shouldReturnCorrectMunicipalityName() throws Exception {
        mockMvc.perform(get("/municipality/id/"+MUNICIPALITY_ID))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains(MUNICIPALITY_NAME));
    }

    @Test
    public void shouldReturnCorrectMunicipalitiesFromConstituency() throws Exception {
        ResultActions result = mockMvc.perform(get("/municipalities/constituency/" + CONSTITUENCY_ID))
                .andExpect(status().isOk());

        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        List<MunicipalityModel> actualMunicipalities = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertThat(actualMunicipalities.size()).isEqualTo(MUNICIPALITIES_CONSTITUENCY.length);
        for (int i = 0; i < actualMunicipalities.size(); i++) {
            assertThat(actualMunicipalities.get(i).getMunicipalityId()).isEqualTo(MUNICIPALITIES_CONSTITUENCY[i].getMunicipalityId());
        }
    }

    @Test
    public void incorrectStringMunicipalityId() throws Exception {
        mockMvc.perform(get("/municipality/id/aaa"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void incorrectNumberMunicipalityId() throws Exception {
        mockMvc.perform(get("/municipality/id/0"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString()).isEqualTo("");
                });
    }
}
