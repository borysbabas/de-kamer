package com.example.backend;

import com.example.backend.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BackendApplicationTests {
	@MockBean
	private CandidateRepository candidateRepository;

	@Test
	void contextLoads() {
		assertNotNull(candidateRepository);
	}

}
