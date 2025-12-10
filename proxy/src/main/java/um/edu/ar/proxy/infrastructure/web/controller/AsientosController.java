package um.edu.ar.proxy.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.proxy.application.service.GestionAsientosService;
import um.edu.ar.proxy.domain.model.Asiento;
import um.edu.ar.proxy.domain.ports.in.ObtenerAsientosPort;
import um.edu.ar.proxy.infrastructure.web.dto.AsientosOcupadosResponse;


import java.util.List;

@RestController
@RequestMapping("/proxy/eventos")
@RequiredArgsConstructor
public class AsientosController {

    private final ObtenerAsientosPort obtenerAsientosPort;

    @GetMapping("/{eventoId}/asientos")
    public ResponseEntity<AsientosOcupadosResponse> getAsientosOcupados(@PathVariable Long eventoId) {
        var asientos = obtenerAsientosPort.obtenerAsientosOcupados(eventoId);

        var response = AsientosOcupadosResponse.fromDomain(eventoId, asientos);
        return ResponseEntity.ok(response);
    }
}




