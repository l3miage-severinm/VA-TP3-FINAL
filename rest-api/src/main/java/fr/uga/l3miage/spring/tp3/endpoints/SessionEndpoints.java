package fr.uga.l3miage.spring.tp3.endpoints;

import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des session")
@RestController
@RequestMapping("/api/sessions")
public interface SessionEndpoints {

    @Operation(description = "Créer une session")
    @ApiResponse(responseCode = "201",description = "La session à bien été créer")
    @ApiResponse(responseCode = "400" ,description = "La session n'a pas être créer", content = @Content(schema = @Schema(implementation = String.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    SessionResponse createSession(@RequestBody SessionCreationRequest request);

    @PostMapping("/endEvaluation")
    @Operation(description = "Faire passer l'état d'une session de l'état EVAL_STARTED à EVAL_ENDED")
    @ApiResponse(responseCode = "200", description = "La session a été terminée avec succès")
    @ApiResponse(responseCode = "409", description = "Conflit : impossible de terminer la session")
    SessionResponse endEvaluation(@RequestParam Long sessionId);

}
