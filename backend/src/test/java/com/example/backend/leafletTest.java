package com.example.backend;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("production")
public class leafletTest {

    private final String CONSTITUENCY_NAME_IN_RESULT = "Amstelveen";
    private final double LON = 4.85149300219;
    private final double LAT = 52.2892743043;
    private final int CONSTITUENCY_ID = 362;
    private final int VOTES_AMOUNT = 23874;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetChartVotesConstituencyAffiliation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/leaflet/get-municipality-votes-by-affiliationId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value(CONSTITUENCY_NAME_IN_RESULT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].value").value(VOTES_AMOUNT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(CONSTITUENCY_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lon").value(LON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].lat").value(LAT));
    }
}
