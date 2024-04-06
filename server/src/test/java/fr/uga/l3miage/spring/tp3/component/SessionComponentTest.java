package fr.uga.l3miage.spring.tp3.component;

import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationStepRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionComponentTest {

    @Autowired
    SessionComponent sessionComponent;

    @MockBean
    EcosSessionRepository ecosSessionRepository;

    @MockBean
    EcosSessionProgrammationRepository ecosSessionProgrammationRepository;

    @Test
    void createSessionSuccess() {

        EcosSessionEntity sessionEntity = EcosSessionEntity.builder()
                .id(1L).examEntities(new HashSet<>())
                .name("test")
                .ecosSessionProgrammationEntity(EcosSessionProgrammationEntity.builder().build())
                .build();

        when(ecosSessionProgrammationRepository.save(any()))
                .thenReturn(sessionEntity.getEcosSessionProgrammationEntity());

        when(ecosSessionRepository.save(any()))
                .thenReturn(sessionEntity);

        EcosSessionEntity createdSession = sessionComponent.createSession(sessionEntity);

        assertNotNull(createdSession);
    }
}
