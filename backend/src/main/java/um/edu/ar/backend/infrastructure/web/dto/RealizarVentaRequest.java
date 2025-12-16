package um.edu.ar.backend.infrastructure.web.dto;

import um.edu.ar.backend.domain.ports.in.RealizarVentaUseCase;

import java.util.List;

public record RealizarVentaRequest(
        List<AsientoVentaDto> asientos
) {
    public record AsientoVentaDto(int fila, int columna, String persona) {}

    public RealizarVentaUseCase.RealizarVentaCommand toCommand(String sessionId, Long eventoId) {
        var asientosCmd = asientos == null
                ? List.<RealizarVentaUseCase.RealizarVentaCommand.AsientoVenta>of()
                : asientos.stream()
                .map(a -> new RealizarVentaUseCase.RealizarVentaCommand.AsientoVenta(
                        a.fila(), a.columna(), a.persona()
                ))
                .toList();

        return new RealizarVentaUseCase.RealizarVentaCommand(
                sessionId,
                eventoId,
                asientosCmd
        );
    }
}

