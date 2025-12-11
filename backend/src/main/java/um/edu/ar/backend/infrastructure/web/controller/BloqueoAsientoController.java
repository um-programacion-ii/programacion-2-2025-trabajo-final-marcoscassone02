package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.model.SessionState;
import um.edu.ar.backend.domain.ports.in.BloquearAsientosUseCase;
import um.edu.ar.backend.domain.ports.out.SesionService;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class BloqueoAsientoController {

    private final BloquearAsientosUseCase bloquearAsientosUseCase;
    private final SesionService sesionService;

    @PostMapping("/{eventoId}/bloquear-asientos")
    public ResponseEntity<BloquearAsientosUseCase.BloquearAsientosResponse> bloquearAsientos(
            @PathVariable Long eventoId,
            @RequestBody BloquearRequest request,
            @RequestHeader("X-Session-Id") String sessionId
    ) {
        if (!sesionService.validarSesion(sessionId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var command = new BloquearAsientosUseCase.BloquearAsientosCommand(
                sessionId,
                eventoId,
                request.asientos()
        );

        var response = bloquearAsientosUseCase.bloquear(command);

        if (response.resultado()) {
            var state = sesionService.obtenerSesion(sessionId);
            if (state != null) {
                var seleccionados = request.asientos().stream()
                        .map(a -> new SessionState.AsientoSeleccionado(
                                a.fila(), a.columna(), null
                        ))
                        .toList();

                state.setPasoActual(SessionState.Step.SELECCION_ASIENTOS);
                state.setEventoId(eventoId);
                state.setAsientos(seleccionados);
                sesionService.guardarSesion(state);
            }
        }

        HttpStatus status = response.resultado()
                ? HttpStatus.OK
                : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(response);
    }

    public record BloquearRequest(
            List<BloquearAsientosUseCase.BloquearAsientosCommand.AsientoRequest> asientos
    ) {}
}




