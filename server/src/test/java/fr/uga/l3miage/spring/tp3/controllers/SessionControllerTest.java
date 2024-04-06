package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class SessionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void CreateSessionSuccess(){

        final org.springframework.http.HttpHeaders headers = new HttpHeaders();
        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        // When
        ResponseEntity<SessionResponse> responseEntity = testRestTemplate.postForEntity("/api/sessions/create", request, SessionResponse.class);

        
        // Then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getBody()).isNotNull();
    }

}
