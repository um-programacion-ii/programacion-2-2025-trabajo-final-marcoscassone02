package um.edu.ar.backend.domain.ports.in;

import java.util.List;

public interface RealizarVentaUseCase {

    RealizarVentaResponse realizarVenta(RealizarVentaCommand command);

    record RealizarVentaCommand(
            String sessionId,
            Long eventoId,
            List<AsientoVenta> asientos
    ) {
        public record AsientoVenta(int fila, int columna, String persona) {}
    }

    record RealizarVentaResponse(
            boolean resultado,
            String descripcion,
            Long eventoId,
            Long ventaId,
            double precioVenta,
            List<AsientoEstado> asientos
    ) {
        public record AsientoEstado(int fila, int columna, String persona, String estado) {}
    }
}

