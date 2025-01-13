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
public class ChartTest {

    private final String AFFILATION_NAME_IN_RESULT = "VVD";
    private final int AFFILATION_VOTES_AMOUNT = 2279130;
    private final int MEMBER_COUNT = 80;
    private final int AFFILATION_ID = 1;
    private final int CONSTITUENCY_ID = 1;
    private final String CONSTITUENCY_NAME_IN_RESULT = "Lelystad";
    private final int CONSTITUENCY_VOTES_AMOUNT = 146088;
    private final int CONSTITUENCY_VOTES_AMOUNT_BY_CANDIDATE = 124755;
    private final String MUNICIPALITY_NAME_IN_RESULT = "'s-Gravenhage";
    private final int MUNICIPALITY_VOTES_AMOUNT = 55197;
    private final int MUNICIPALITY_ID = 1;
    private final String MUNICIPALITY_NAME_BY_AFFILITON = "Amstelveen";
    private final int MUNICIPALITY_VOTES_AMOUNT_BY_AFFILITON = 23874;
    private final String CANIDATE_NAME = "M. Rutte";


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetChartVotes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chart/votes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].x").value(CANIDATE_NAME));
    }

    @Test
    public void testGetChartVotesAffiliations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chart/votes/affiliations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].x").value(AFFILATION_NAME_IN_RESULT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].y").value(AFFILATION_VOTES_AMOUNT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(AFFILATION_ID));
    }

    @Test
    public void testGetChartVotesConstituencyAffiliation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chart/votes/constituency/affiliation/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].x").value(CONSTITUENCY_NAME_IN_RESULT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].y").value(CONSTITUENCY_VOTES_AMOUNT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(CONSTITUENCY_ID));
    }

    @Test
    public void testGetChartVotesConstituencyAffiliationCandidate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chart/votes/constituency/affiliation/1/candidate/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].x").value(CONSTITUENCY_NAME_IN_RESULT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].y").value(CONSTITUENCY_VOTES_AMOUNT_BY_CANDIDATE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(CONSTITUENCY_ID));
    }

    @Test
    public void testGetChartMembersCount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chart/members/count"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].x").value(AFFILATION_NAME_IN_RESULT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].y").value(MEMBER_COUNT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(AFFILATION_ID));
    }

    @Test
    public void testGetMunicipalityVotesByCandidateId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chart/get-municipality-votes-by-candidateId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].x").value(MUNICIPALITY_NAME_IN_RESULT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].y").value(MUNICIPALITY_VOTES_AMOUNT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(MUNICIPALITY_ID));
    }

    @Test
    public void testGetMunicipalityVotesByAffiliationId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chart/get-municipality-votes-by-affiliationId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].x").value(MUNICIPALITY_NAME_BY_AFFILITON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].y").value(MUNICIPALITY_VOTES_AMOUNT_BY_AFFILITON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(MUNICIPALITY_ID));
    }
}