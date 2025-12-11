package um.edu.ar.backend.infrastructure.web.dto;

import java.time.Instant;

public record VentaResumenResponse(
        Long eventoId,
        Long ventaId,
        Instant fechaVenta,
        boolean resultado,
        String descripcion,
        double precioVenta,
        int cantidadAsientos
) {}
