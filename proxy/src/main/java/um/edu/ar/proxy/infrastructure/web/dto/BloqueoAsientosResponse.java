package um.edu.ar.proxy.infrastructure.web.dto;

import java.util.List;

public record BloqueoAsientosResponse(
        boolean resultado,
        String descripcion,
        Long eventoId,
        List<AsientoEstadoDto> asientos
) {
    public record AsientoEstadoDto(
            String estado,
            int fila,
            int columna
    ) {}
}

