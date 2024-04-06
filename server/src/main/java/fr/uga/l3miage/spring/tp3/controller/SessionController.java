package fr.uga.l3miage.spring.tp3.controller;

import fr.uga.l3miage.spring.tp3.endpoints.SessionEndpoints;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import fr.uga.l3miage.spring.tp3.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SessionController implements SessionEndpoints {
    private final SessionService sessionService;

    @Override
    public SessionResponse createSession(SessionCreationRequest request) {
        return sessionService.createSession(request);
    }

    @Override
    public SessionResponse endEvaluation(Long sessionId) {
        return sessionService.endSession(sessionId);
    }
}
