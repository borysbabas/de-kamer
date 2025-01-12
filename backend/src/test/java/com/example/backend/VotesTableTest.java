package com.example.backend;

import com.example.backend.controller.ConstituencyController;
import com.example.backend.controller.MunicipalityController;
import com.example.backend.controller.PollingStationController;
import com.example.backend.controller.VotesTableController;
import com.example.backend.services.ConstituencyService;
import com.example.backend.services.MunicipalityService;
import com.example.backend.services.PollingStationService;
import com.example.backend.services.VotesTableService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("production")
public class VotesTableTest {
    @Autowired
    private VotesTableController votesTableController;

    @Autowired
    private ConstituencyController constituencyController;

    @Autowired
    private MunicipalityController municipalityController;

    @Autowired
    private PollingStationController pollingStationController;

    @Autowired
    private VotesTableService votesTableService;

    @Autowired
    private ConstituencyService constituencyService;

    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private PollingStationService pollingStationService;

    @Autowired
    private MockMvc mockMvc;

    private final String CONSTITUENCY_IN_RESULT = "Groningen";
    private final String CONSTITUENCY_NOT_IN_RESULT = "Bonaire";
    private final String MUNICIPALITY_IN_RESULT = "Groningen";
    private final String POLLING_STATION_IN_RESULT = "Stembureau Oude Boteringestraat 33";
    private final int CONSTITUENCY_VOTES_AMOUNT = 943;
    private final String CANDIDATE_IN_CONSTITUENCY_RESULT = "Stieneke van der Graaf";
    private final int MUNICIPALITY_VOTES_AMOUNT = 810;
    private final String CANDIDATE_IN_MUNICIPALITY_RESULT = "Corinne de Jonge van Ellemeet";
    private final int POLLING_STATION_VOTES_AMOUNT = 139;
    private final String CANDIDATE_IN_POLLING_STATION_RESULT = "Carline van Breugel";


    @Test
    public void contextLoads() {
        assertThat(votesTableController).isNotNull();
        assertThat(constituencyController).isNotNull();
        assertThat(municipalityController).isNotNull();
        assertThat(pollingStationController).isNotNull();
        assertThat(votesTableService).isNotNull();
        assertThat(constituencyService).isNotNull();
        assertThat(municipalityService).isNotNull();
        assertThat(pollingStationService).isNotNull();
    }

    @Nested
    class GeneralVotesTableTests {
        @Test
        public void getConstituencies() throws Exception {
            mockMvc.perform(get("/constituencies"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains(CONSTITUENCY_IN_RESULT);
                        assertThat(content).doesNotContain(CONSTITUENCY_NOT_IN_RESULT);
                    });
        }

        @Test
        public void getMunicipalities() throws Exception {
            mockMvc.perform(get("/municipalities/constituency/1"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains(MUNICIPALITY_IN_RESULT);
                    });
        }

        @Test
        public void getPollingStations() throws Exception {
            mockMvc.perform(get("/polling-station/municipality/14"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                       String content = result.getResponse().getContentAsString();
                        System.out.println(content);
                       assertThat(content).contains(POLLING_STATION_IN_RESULT);
                    });
        }

        @Test
        public void getConstituencyVotesCount() throws Exception {
            mockMvc.perform(get("/votes/constituencies/1/count"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(Long.parseLong(content)).isEqualTo(CONSTITUENCY_VOTES_AMOUNT);
                    });
        }

        @Test
        public void getConstituencyVotesFirstPage() throws Exception {
            mockMvc.perform(get("/votes/constituencies/1/20/1"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains(CANDIDATE_IN_CONSTITUENCY_RESULT);
                    });
        }

        @Test
        public void getMunicipalityVotesCount() throws Exception {
            mockMvc.perform(get("/votes/municipalities/14/count"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(Long.parseLong(content)).isEqualTo(MUNICIPALITY_VOTES_AMOUNT);
                    });
        }

        @Test
        public void getMunicipalityVotesFirstPage() throws Exception {
            mockMvc.perform(get("/votes/municipalities/14/20/1"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains(CANDIDATE_IN_MUNICIPALITY_RESULT);
                    });
        }

        @Test
        public void getPollingStationVotesCount() throws Exception {
            mockMvc.perform(get("/votes/polling-stations/5870/count"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(Long.parseLong(content)).isEqualTo(POLLING_STATION_VOTES_AMOUNT);
                    });
        }

        @Test
        public void getPollingStationVotesFirstPage() throws Exception {
            mockMvc.perform(get("/votes/polling-stations/5870/20/1"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains(CANDIDATE_IN_POLLING_STATION_RESULT);
                    });
        }
    }
}

// 5870