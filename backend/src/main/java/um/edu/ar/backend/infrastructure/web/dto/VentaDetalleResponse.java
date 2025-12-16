package um.edu.ar.backend.infrastructure.web.dto;

import java.time.Instant;
import java.util.List;

public record VentaDetalleResponse(
        Long eventoId,
        Long ventaId,
        Instant fechaVenta,
        List<AsientoVentaResponse> asientos,
        boolean resultado,
        String descripcion,
        double precioVenta
) {}
