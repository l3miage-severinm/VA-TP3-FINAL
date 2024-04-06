package fr.uga.l3miage.spring.tp3.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class SessionCreationRequest {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<Long> examsId;
    private SessionProgrammationCreationRequest ecosSessionProgrammation;
}
