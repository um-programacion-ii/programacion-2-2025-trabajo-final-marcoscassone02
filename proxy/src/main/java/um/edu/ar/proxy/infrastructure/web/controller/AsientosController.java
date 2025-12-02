package um.edu.ar.proxy.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.proxy.application.service.GestionAsientosService;
import um.edu.ar.proxy.domain.model.Asiento;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class AsientosController {

    private final GestionAsientosService service;

    @GetMapping("/proxy/eventos/{eventoId}/asientos")
    public ResponseEntity<List<Asiento>> obtenerAsientos(
            @PathVariable Long eventoId,
            @RequestParam int filas,
            @RequestParam int columnas
    ) {
        List<Asiento> mapa = service.obtenerMapaAsientos(eventoId, filas, columnas);
        return ResponseEntity.ok(mapa);
    }

}




