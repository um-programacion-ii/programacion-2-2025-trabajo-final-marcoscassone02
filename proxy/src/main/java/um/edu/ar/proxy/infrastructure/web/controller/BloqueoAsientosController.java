package um.edu.ar.proxy.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.proxy.application.service.GestionAsientosService;
import um.edu.ar.proxy.infrastructure.web.dto.BloquearAsientosRequest;
import um.edu.ar.proxy.infrastructure.web.dto.BloqueoAsientosResponse;

@RestController
@RequestMapping("/api/catedra")
@RequiredArgsConstructor
public class BloqueoAsientosController {

    private final GestionAsientosService gestionAsientosService;

    @PostMapping("/eventos/{eventoId}/bloquear-asientos")
    public ResponseEntity<BloqueoAsientosResponse> bloquearAsientos(
            @PathVariable Long eventoId,
            @RequestHeader("Authorization") String authorization,
            @RequestBody BloquearAsientosRequest request
    ) {
        BloqueoAsientosResponse respuesta =
                gestionAsientosService.bloquearAsientos(eventoId, request, authorization);

        return ResponseEntity.ok(respuesta);
    }
}

