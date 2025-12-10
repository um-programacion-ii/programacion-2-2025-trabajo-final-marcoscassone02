package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.ports.in.BloquearAsientosUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class BloqueoAsientoController {

    private final BloquearAsientosUseCase bloquearAsientosUseCase;

    @PostMapping("/{eventoId}/bloquear-asientos")
    public ResponseEntity<BloquearAsientosUseCase.BloquearAsientosResponse> bloquearAsientos(
            @PathVariable Long eventoId,
            @RequestBody BloquearRequest request,
            @RequestHeader("X-Session-Id") String sessionId
    ) {

        var command = new BloquearAsientosUseCase.BloquearAsientosCommand(
                sessionId,
                eventoId,
                request.asientos()
        );

        var response = bloquearAsientosUseCase.bloquear(command);

        HttpStatus status = response.resultado()
                ? HttpStatus.OK
                : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(response);
    }

    public record BloquearRequest(
            List<BloquearAsientosUseCase.BloquearAsientosCommand.AsientoRequest> asientos
    ) {}
}



