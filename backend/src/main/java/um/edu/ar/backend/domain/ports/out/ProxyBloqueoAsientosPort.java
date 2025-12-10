package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.AsientoId;

import java.util.List;

public interface ProxyBloqueoAsientosPort {

    BloqueoResultado bloquearAsientos(
            Long eventoId,
            List<AsientoId> asientos
    );

    record BloqueoResultado(
            boolean resultado,
            String descripcion,
            Long eventoId,
            List<AsientoBloqueadoRespuesta> asientos
    ) {
        public record AsientoBloqueadoRespuesta(
                int fila,
                int columna,
                String estado
        ) {}
    }
}


