package com.example.backend;

import com.example.backend.controller.ConstituencyController;
import com.example.backend.controller.VotesTableController;
import com.example.backend.services.ConstituencyService;
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
    private VotesTableService votesTableService;

    @Autowired
    private ConstituencyService constituencyService;

    @Autowired
    private MockMvc mockMvc;

    private final String CONSTITUENCY_IN_RESULT = "Groningen";
    private final String CONSTITUENCY_NOT_IN_RESULT = "Bonaire";
    private final int CONSTITUENCY_VOTES_AMOUNT = 943;
    private final String CANDIDATE_IN_RESULT = "Stieneke van der Graaf";

    @Test
    public void contextLoads() {
        assertThat(votesTableController).isNotNull();
        assertThat(constituencyController).isNotNull();
        assertThat(votesTableService).isNotNull();
        assertThat(constituencyService).isNotNull();
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
                        assertThat(content).contains(CANDIDATE_IN_RESULT);
                    });
        }
    }
}
