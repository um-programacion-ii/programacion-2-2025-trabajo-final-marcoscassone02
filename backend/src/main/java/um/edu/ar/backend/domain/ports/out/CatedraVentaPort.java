package um.edu.ar.backend.domain.ports.out;

import java.util.List;

public interface CatedraVentaPort {

    VentaResultado realizarVenta(
            Long eventoId,
            double precioVenta,
            List<AsientoVentaRequest> asientos
    );

    record AsientoVentaRequest(int fila, int columna, String persona) {}

    record VentaResultado(
            boolean resultado,
            String descripcion,
            Long eventoId,
            Long ventaId,
            double precioVenta,
            List<AsientoVentaRespuesta> asientos
    ) {
        public record AsientoVentaRespuesta(
                int fila,
                int columna,
                String persona,
                String estado
        ) {}
    }
}

