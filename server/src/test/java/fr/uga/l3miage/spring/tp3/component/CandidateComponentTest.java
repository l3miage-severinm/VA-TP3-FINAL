package fr.uga.l3miage.spring.tp3.component;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateComponentTest {

    @Autowired
    private CandidateComponent candidateComponent;

    @MockBean
    private CandidateRepository candidateRepository;

    @Test
    void getCandidateByIdThrowsNotFoundException() {
        // Given
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then - When
        assertThrows(CandidateNotFoundException.class, () -> candidateComponent.getCandidatById(1L));
    }

    @Test
    void getCandidateByIdNoException() throws CandidateNotFoundException {
        // Given
        CandidateEntity candidateEntity = CandidateEntity.builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .build();
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.of(candidateEntity));

        // When - Then
        assertDoesNotThrow(() -> candidateComponent.getCandidatById(1L));
    }

    @Test
    void addCandidateThrowsException() throws IllegalArgumentException{
        // Given
        CandidateEntity candidateEntity = CandidateEntity.builder()
                .email("test.com")
                .birthDate(LocalDate.now())
                .build();
        /*
        HashSet candidates = new HashSet();
        candidates.add(candidateEntity);
        when(candidateRepository.findAll()).thenReturn(candidates);
        assert( candidateComponent.addCandidates(candidates) == null);
        */
        
    }
}

