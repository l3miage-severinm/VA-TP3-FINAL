package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionServiceTest {

    @Autowired
    SessionService sessionService;

    @MockBean
    SessionComponent sessionComponent;

    @Test
    void createSessionSuccess() {

        // Given
        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        EcosSessionEntity ecosSessionEntity = EcosSessionEntity.builder()
                .name("test")
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .build();

        when(sessionComponent.createSession(any())).thenReturn(ecosSessionEntity);

        // When
        SessionResponse response = sessionService.createSession(request);

        // Then
        assertNotNull(response);
    }

    @Test
    void createSessionFailure() {
        // Given
        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        when(sessionComponent.createSession(any())).thenReturn(null);

        // When
        SessionResponse response = sessionService.createSession(request);

        // Then
        //assertNotNull(response);
    }

    @Test
    void endSessionSuccess() {
        // Given
        EcosSessionEntity ecosSessionEntity = EcosSessionEntity.builder()
                .name("test")
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .build();

        when(sessionComponent.endSession(any())).thenReturn(ecosSessionEntity);

        // When
        SessionResponse response = sessionService.endSession(1L);

        // Then
        assertNotNull(response);
    }
}
