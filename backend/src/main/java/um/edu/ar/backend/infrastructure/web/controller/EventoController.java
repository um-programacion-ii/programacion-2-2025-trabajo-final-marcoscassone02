package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.model.SessionState;
import um.edu.ar.backend.domain.ports.in.GestionEventosUseCase;
import um.edu.ar.backend.domain.ports.in.GestionAsientosUseCase;
import um.edu.ar.backend.domain.ports.out.SesionService;
import um.edu.ar.backend.infrastructure.web.dto.EventoDetalleResponse;
import um.edu.ar.backend.infrastructure.web.dto.EventoResponse;
import um.edu.ar.backend.infrastructure.web.dto.AsientoResponse;
import um.edu.ar.backend.infrastructure.web.mapper.EventoDtoMapper;
import um.edu.ar.backend.infrastructure.web.mapper.AsientoDtoMapper;
import um.edu.ar.backend.infrastructure.http.config.CatedraEventoClient;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final CatedraEventoClient catedraEventoClient;
    private final GestionEventosUseCase gestionEventosUseCase;
    private final GestionAsientosUseCase gestionAsientosUseCase;
    private final EventoDtoMapper eventoDtoMapper;
    private final AsientoDtoMapper asientoDtoMapper;
    private final SesionService sesionService;

    @GetMapping
    public ResponseEntity<List<EventoResponse>> getAll() {
        var eventos = gestionEventosUseCase.listarEventos();
        return ResponseEntity.ok(eventoDtoMapper.toResponseList(eventos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> getById(
            @RequestHeader("X-Session-Id") String sessionId,
            @PathVariable Long id
    ) {
        if (!sesionService.validarSesion(sessionId)) {
            return ResponseEntity.status(401).build();
        }

        var maybeEvento = gestionEventosUseCase.obtenerEventoPorId(id);
        if (maybeEvento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var state = sesionService.obtenerSesion(sessionId);
        if (state != null) {
            state.setPasoActual(SessionState.Step.DETALLE_EVENTO);
            state.setEventoId(id);
            sesionService.guardarSesion(state);
        }

        return ResponseEntity.ok(eventoDtoMapper.toResponse(maybeEvento.get()));
    }

    @GetMapping("/{id}/asientos")
    public ResponseEntity<List<AsientoResponse>> getAsientos(
            @RequestHeader("X-Session-Id") String sessionId,
            @PathVariable Long id
    ) {
        if (!sesionService.validarSesion(sessionId)) {
            return ResponseEntity.status(401).build();
        }

        var maybeEvento = gestionEventosUseCase.obtenerEventoPorId(id);
        if (maybeEvento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var evento = maybeEvento.get();
        var asientos = gestionAsientosUseCase.obtenerAsientosEvento(
                evento.getId(),
                evento.getFilas(),
                evento.getColumnas()
        );

        var response = asientoDtoMapper.toResponseList(asientos);

        var state = sesionService.obtenerSesion(sessionId);
        if (state != null) {
            state.setPasoActual(SessionState.Step.SELECCION_ASIENTOS);
            state.setEventoId(id);
            sesionService.guardarSesion(state);
        }

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/detalle")
    public ResponseEntity<EventoDetalleResponse> getDetalle(@PathVariable Long id) {
        var detalle = catedraEventoClient.obtenerDetalleEvento(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalle);
    }
}

