package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.ports.in.RealizarVentaUseCase;
import um.edu.ar.backend.infrastructure.web.dto.RealizarVentaRequest;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class VentaController {

    private final RealizarVentaUseCase realizarVentaUseCase;

    @PostMapping("/{eventoId}/realizar-venta")
    public ResponseEntity<?> realizarVenta(
            @RequestHeader("X-Session-Id") String sessionId,
            @PathVariable Long eventoId,
            @RequestBody RealizarVentaRequest request
    ) {
        var command = request.toCommand(sessionId, eventoId);
        var result = realizarVentaUseCase.realizarVenta(command);
        return ResponseEntity.ok(result);
    }
}
