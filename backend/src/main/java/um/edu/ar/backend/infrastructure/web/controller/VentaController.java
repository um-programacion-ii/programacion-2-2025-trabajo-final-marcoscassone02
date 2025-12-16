package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.model.SessionState;
import um.edu.ar.backend.domain.ports.in.RealizarVentaUseCase;
import um.edu.ar.backend.domain.ports.out.SesionService;
import um.edu.ar.backend.infrastructure.web.dto.RealizarVentaRequest;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class VentaController {

    private final RealizarVentaUseCase realizarVentaUseCase;
    private final SesionService sesionService;

    @PostMapping("/{eventoId}/realizar-venta")
    public ResponseEntity<?> realizarVenta(
            @RequestHeader("X-Session-Id") String sessionId,
            @PathVariable Long eventoId,
            @RequestBody RealizarVentaRequest request
    ) {
        if (!sesionService.validarSesion(sessionId)) {
            return ResponseEntity.status(401).build();
        }

        var command = request.toCommand(sessionId, eventoId);
        var result = realizarVentaUseCase.realizarVenta(command);

        if (result.resultado()) {
            var state = sesionService.obtenerSesion(sessionId);
            if (state != null) {
                state.setPasoActual(SessionState.Step.VENTA_COMPLETADA);
                sesionService.guardarSesion(state);
            }
        }

        return ResponseEntity.ok(result);
    }
}

