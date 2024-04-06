package fr.uga.l3miage.spring.tp3.controller;

import fr.uga.l3miage.spring.tp3.endpoints.CandidateEndpoints;
import fr.uga.l3miage.spring.tp3.services.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.HashSet;

@Controller
@RequiredArgsConstructor
public class CandidateController implements CandidateEndpoints {
    private final CandidateService candidateService;

    @Override
    public Double getCandidateAverage(Long candidateId) {
        return candidateService.getCandidateAverage(candidateId);
    }

    @Override
    public void addStudentsToTestCenter(HashSet candidates) {
        candidateService.addCandidates(candidates);
    }
}
