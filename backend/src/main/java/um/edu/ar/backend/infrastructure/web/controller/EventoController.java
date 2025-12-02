package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.ports.in.GestionEventosUseCase;
import um.edu.ar.backend.domain.ports.in.GestionAsientosUseCase;
import um.edu.ar.backend.infrastructure.web.dto.EventoResponse;
import um.edu.ar.backend.infrastructure.web.dto.AsientoResponse;
import um.edu.ar.backend.infrastructure.web.mapper.EventoDtoMapper;
import um.edu.ar.backend.infrastructure.web.mapper.AsientoDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final GestionEventosUseCase gestionEventosUseCase;
    private final GestionAsientosUseCase gestionAsientosUseCase;
    private final EventoDtoMapper eventoDtoMapper;
    private final AsientoDtoMapper asientoDtoMapper;

    @GetMapping
    public ResponseEntity<List<EventoResponse>> getAll() {
        var eventos = gestionEventosUseCase.listarEventos();
        return ResponseEntity.ok(eventoDtoMapper.toResponseList(eventos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> getById(@PathVariable Long id) {
        return gestionEventosUseCase.obtenerEventoPorId(id)
                .map(eventoDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/asientos")
    public ResponseEntity<List<AsientoResponse>> getAsientos(@PathVariable Long id) {

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
        return ResponseEntity.ok(response);
    }
}
