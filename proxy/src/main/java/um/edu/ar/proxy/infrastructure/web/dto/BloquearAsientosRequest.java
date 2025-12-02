package um.edu.ar.proxy.infrastructure.web.dto;

import java.util.List;

public record BloquearAsientosRequest(
        List<AsientoRequest> asientos
) {
    public record AsientoRequest(
            int fila,
            int columna
    ) {}
}


