package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.ports.in.BloquearAsientosUseCase;
import um.edu.ar.backend.domain.ports.out.SesionTokenService;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoAsientoController {

    private final BloquearAsientosUseCase bloquearAsientosUseCase;
    private final SesionTokenService sesionTokenService;

    @PostMapping("/{eventoId}/bloquear-asientos")
    public ResponseEntity<BloquearAsientosUseCase.BloquearAsientosResponse> bloquearAsientos(
            @PathVariable Long eventoId,
            @RequestBody BloquearRequest request,
            @RequestHeader("X-Session-Id") String sessionId   // <-- AHORA ES String
    ) {
        // 1) Validar que exista un token de la cátedra asociado a esta sesión
        var token = sesionTokenService.obtenerToken(sessionId);  // <-- ya no hace falta String.valueOf
        if (token == null) {
            var errorResponse = new BloquearAsientosUseCase.BloquearAsientosResponse(
                    false,
                    "Sesión no válida o sin token de autenticación",
                    eventoId,
                    List.of()   // lista vacía de asientos
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        var command = new BloquearAsientosUseCase.BloquearAsientosCommand(
                sessionId,
                eventoId,
                request.asientos()
        );

        var response = bloquearAsientosUseCase.bloquear(command);
        return ResponseEntity.ok(response);
    }

    public record BloquearRequest(
            List<BloquearAsientosUseCase.BloquearAsientosCommand.AsientoRequest> asientos
    ) {}
}



