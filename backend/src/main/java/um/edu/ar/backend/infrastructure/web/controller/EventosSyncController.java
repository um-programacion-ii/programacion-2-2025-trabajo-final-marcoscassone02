package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.application.service.SincronizarEventosService;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventosSyncController {

    private final SincronizarEventosService sincronizarEventosService;

    @PostMapping("/sincronizar")
    public ResponseEntity<Void> sincronizar() {
        sincronizarEventosService.sincronizarDesdeCatedra();
        return ResponseEntity.accepted().build();
    }
}