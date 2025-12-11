package um.edu.ar.backend.infrastructure.web.dto;

public record AsientoVentaResponse(
        int fila,
        int columna,
        String persona,
        String estado
) {}
