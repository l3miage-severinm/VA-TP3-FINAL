package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;

import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateServiceTest {

    @Autowired
    CandidateService candidateService;

    @MockBean
    CandidateComponent candidateComponent;

    @Test
    void GetCandidateAverageFound() throws CandidateNotFoundException {

        // Given
        CandidateEntity candidateEntity = CandidateEntity.builder()
                .id(1L)
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .candidateEvaluationGridEntities(new HashSet<>())
                .build();
        CandidateEvaluationGridEntity grid1 = CandidateEvaluationGridEntity.builder()
                .grade(5).examEntity(ExamEntity.builder().weight(1).build()).build();
        CandidateEvaluationGridEntity grid2 = CandidateEvaluationGridEntity.builder()
                .grade(7).examEntity(ExamEntity.builder().weight(1).build()).build();

        candidateEntity.getCandidateEvaluationGridEntities().add(grid1);
        candidateEntity.getCandidateEvaluationGridEntities().add(grid2);

        when(candidateComponent.getCandidatById(1L)).thenReturn(candidateEntity);

        // When
        double average = candidateService.getCandidateAverage(1L);

        // Then
        assertEquals(6.0, average);
    }

    @Test
    void GetCandidateAverageNotFound() throws CandidateNotFoundException {
        // Given
        when(candidateComponent.getCandidatById(1L)).thenThrow(new CandidateNotFoundException("Candidate not found", 1L));

        // When - Then
        assertThrows(CandidateNotFoundRestException.class, () -> candidateService.getCandidateAverage(1L));
    }



}
