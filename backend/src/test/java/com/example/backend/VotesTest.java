package com.example.backend;

import com.example.backend.controller.VotesController;
import com.example.backend.services.AffiliationVotesService;
import com.example.backend.services.CountryVotesService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("production")
public class VotesTest {
    @Autowired
    private VotesController VotesController;

    @Autowired
    private AffiliationVotesService affiliationService;

    @Autowired
    private CountryVotesService countryVotesService;

    @Autowired
    private MockMvc mockMvc;

    private final String AFFILIATION_IN_RESULT = "VVD";
    private final Number AFFILIATION_VOTES_AMOUNT = 2279130;
    private final String CANDIDATE_IN_RESULT = "M. Rutte";
    private final Number CANDIDATE_VOTES_AMOUNT = 1977651;

    @Test
    public void contextLoads() {
        assertThat(VotesController).isNotNull();
        assertThat(affiliationService).isNotNull();
        assertThat(countryVotesService).isNotNull();
    }

    @Nested
    class GeneralVotesTests {
        @Test
        public void getAffiliationByName() throws Exception{
            mockMvc.perform(get("/votes/affiliations"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains(AFFILIATION_IN_RESULT);
                        assertThat(content).contains(AFFILIATION_VOTES_AMOUNT.toString());
            });

        }

        @Test
        public void getTotalCandidateVotes() throws Exception {
            mockMvc.perform(get("/votes/candidates"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains(CANDIDATE_IN_RESULT);
                        assertThat(content).contains(CANDIDATE_VOTES_AMOUNT.toString());
                    });
        }
    }
}
