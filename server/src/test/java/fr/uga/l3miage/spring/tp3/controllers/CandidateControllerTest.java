package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CandidateRepository candidateRepository;

    @SpyBean
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;

    @AfterEach
    public void clear() {
        candidateRepository.deleteAll();
    }

    @Test
    void getCandidateAverage10() {

        final HttpHeaders headers = new HttpHeaders();

        final SessionCreationRequest request = SessionCreationRequest
                .builder()
                .build();

        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGrid1 = CandidateEvaluationGridEntity
                .builder()
                .grade(5)
                .candidateEntity(candidate)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGrid2 = CandidateEvaluationGridEntity
                .builder()
                .grade(15)
                .candidateEntity(candidate)
                .build();

        candidateRepository.save(candidate);
        candidateEvaluationGridRepository.save(candidateEvaluationGrid1);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(6);

        assertThat(candidateEntitiesResponses).hasSize(1);
    }
}
