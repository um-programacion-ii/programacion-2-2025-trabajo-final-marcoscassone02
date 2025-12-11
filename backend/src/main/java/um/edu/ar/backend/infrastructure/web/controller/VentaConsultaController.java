package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.ports.in.ListarVentasUseCase;
import um.edu.ar.backend.domain.ports.in.ObtenerVentaPorIdUseCase;
import um.edu.ar.backend.infrastructure.web.dto.AsientoVentaResponse;
import um.edu.ar.backend.infrastructure.web.dto.VentaDetalleResponse;
import um.edu.ar.backend.infrastructure.web.dto.VentaResumenResponse;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaConsultaController {

    private final ListarVentasUseCase listarVentasUseCase;
    private final ObtenerVentaPorIdUseCase obtenerVentaPorIdUseCase;

    @GetMapping
    public ResponseEntity<List<VentaResumenResponse>> listarVentas() {
        var ventas = listarVentasUseCase.listarVentas();

        var response = ventas.stream()
                .map(v -> new VentaResumenResponse(
                        v.getEventoId(),
                        v.getVentaIdCatedra(),
                        v.getFechaVenta(),
                        v.isResultado(),
                        v.getDescripcion(),
                        v.getPrecioVenta(),
                        v.getAsientos() != null ? v.getAsientos().size() : 0
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ventaId}")
    public ResponseEntity<VentaDetalleResponse> obtenerVenta(@PathVariable Long ventaId) {

        var ventaOpt = obtenerVentaPorIdUseCase.obtenerVenta(ventaId);

        if (ventaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var v = ventaOpt.get();

        var asientosResp = v.getAsientos() == null
                ? List.<AsientoVentaResponse>of()
                : v.getAsientos().stream()
                .map(a -> new AsientoVentaResponse(
                        a.getFila(),
                        a.getColumna(),
                        a.getPersona(),
                        a.getEstado()
                ))
                .toList();

        var response = new VentaDetalleResponse(
                v.getEventoId(),
                v.getVentaIdCatedra(),
                v.getFechaVenta(),
                asientosResp,
                v.isResultado(),
                v.getDescripcion(),
                v.getPrecioVenta()
        );

        return ResponseEntity.ok(response);
    }
}

