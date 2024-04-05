package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateRepositoryTest {

    @Autowired
    private TestCenterRepository testCenterRepository;

    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @AfterEach
    public void cleanUp() {
        candidateEvaluationGridRepository.deleteAll();
        candidateRepository.deleteAll();
        testCenterRepository.deleteAll();
    }

    @Test
    void findAllByTestCenterEntityCodeFound() {
        //given
        TestCenterEntity testCenter = TestCenterEntity.builder().code(TestCenterCode.GRE).build();

        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .testCenterEntity(testCenter)
                .build();

        testCenterRepository.save(testCenter);
        candidateRepository.save(candidate);

        //when
        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.GRE);

        //then
        assertThat(candidateEntitiesResponses).hasSize(1);
        assertThat(candidateEntitiesResponses.stream().findFirst().get().getTestCenterEntity().getCode()).isEqualTo(TestCenterCode.GRE);
    }

    @Test
    void findAllByTestCenterEntityCodeNotFound() {

        CandidateEntity candidate = CandidateEntity.builder().email("lorenzo.porcu@etu.univ-grenoble-alpes.fr").build();

        candidateRepository.save(candidate);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.GRE);

        assertThat(candidateEntitiesResponses).hasSize(0);
    }

    @Test
    void findAllByCandidateEvaluationGridEntitiesGradeLessThanFound() {

        CandidateEvaluationGridEntity candidateEvaluationGrid = CandidateEvaluationGridEntity
                .builder()
                .grade(5)
                .build();

        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .build();

        candidateEvaluationGrid.setCandidateEntity(candidate);

        candidateRepository.save(candidate);
        candidateEvaluationGridRepository.save(candidateEvaluationGrid);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(6);

        assertThat(candidateEntitiesResponses).hasSize(1);
    }

    @Test
    void findAllByCandidateEvaluationGridEntitiesGradeLessThanNotFound() {

        CandidateEvaluationGridEntity candidateEvaluationGrid = CandidateEvaluationGridEntity
                .builder()
                .grade(7)
                .build();

        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .build();

        candidateEvaluationGrid.setCandidateEntity(candidate);

        candidateRepository.save(candidate);
        candidateEvaluationGridRepository.save(candidateEvaluationGrid);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(6);

        assertThat(candidateEntitiesResponses).hasSize(0);
    }

    @Test
    void findAllByHasExtraTimeFalseAndBirthDateBefore() {
        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        candidateRepository.save(candidate);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2001, 1, 1));

        assertThat(candidateEntitiesResponses).hasSize(1);
    }

    @Test
    void findAllByHasExtraTimeFalseAndBirthDateAfter() {
        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        candidateRepository.save(candidate);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(1999, 1, 1));

        assertThat(candidateEntitiesResponses).hasSize(0);
    }

    @Test
    void findAllByHasExtraTimeTrueAndBirthDateBefore() {
        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .hasExtraTime(true)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        candidateRepository.save(candidate);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2001, 1, 1));

        assertThat(candidateEntitiesResponses).hasSize(0);
    }

    @Test
    void findAllByHasExtraTimeTrueAndBirthDateAfter() {
        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("lorenzo.porcu@etu.univ-grenoble-alpes.fr")
                .hasExtraTime(true)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        candidateRepository.save(candidate);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(1999, 1, 1));

        assertThat(candidateEntitiesResponses).hasSize(0);
    }
}
