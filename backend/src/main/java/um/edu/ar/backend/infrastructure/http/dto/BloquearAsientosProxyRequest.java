package um.edu.ar.backend.infrastructure.http.dto;


import java.util.List;

public record BloquearAsientosProxyRequest(
        Long eventoId,
        List<AsientoProxyRequest> asientos
) {}
