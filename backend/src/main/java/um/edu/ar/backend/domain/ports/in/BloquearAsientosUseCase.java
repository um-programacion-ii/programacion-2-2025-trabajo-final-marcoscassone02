package um.edu.ar.backend.domain.ports.in;

import java.util.List;

public interface BloquearAsientosUseCase {

    BloquearAsientosResponse bloquear(BloquearAsientosCommand command);

    record BloquearAsientosCommand(
            String sessionId,
            Long eventoId,
            List<AsientoRequest> asientos
    ) {
        public record AsientoRequest(int fila, int columna) {}
    }

    record BloquearAsientosResponse(
            boolean resultado,
            String descripcion,
            Long eventoId,
            List<AsientoEstado> asientos
    ) {
        public record AsientoEstado(
                int fila,
                int columna,
                String estado
        ) {}
    }
}
