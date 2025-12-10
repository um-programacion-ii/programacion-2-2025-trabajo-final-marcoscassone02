package um.edu.ar.proxy.infrastructure.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.proxy.application.service.VentaProxyService;
import um.edu.ar.proxy.infrastructure.web.dto.CatedraVentaResponse;
import um.edu.ar.proxy.infrastructure.web.dto.VentaRequest;

@RestController
@RequestMapping("/proxy")
@RequiredArgsConstructor
public class VentaProxyController {

    private final VentaProxyService ventaProxyService;

    @PostMapping("/eventos/{eventoId}/venta")
    public ResponseEntity<CatedraVentaResponse> realizarVenta(
            @PathVariable Long eventoId,
            @RequestBody VentaRequest request
    ) {
        var response = ventaProxyService.realizarVenta(eventoId, request);
        return ResponseEntity.ok(response);
    }
}
