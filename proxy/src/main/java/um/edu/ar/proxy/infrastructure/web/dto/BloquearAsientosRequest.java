package um.edu.ar.proxy.infrastructure.web.dto;

import lombok.Data;
import um.edu.ar.proxy.domain.model.BloquearAsientosCommand;

import java.util.List;

@Data
public class BloquearAsientosRequest {
    private Long eventoId;
    private List<AsientoRequest> asientos;

    @Data
    public static class AsientoRequest {
        private int fila;
        private int columna;
    }

    public BloquearAsientosCommand toCommand() {
        List<BloquearAsientosCommand.ParAsiento> pares = asientos.stream()
                .map(a -> new BloquearAsientosCommand.ParAsiento(a.getFila(), a.getColumna()))
                .toList();
        return new BloquearAsientosCommand(eventoId, pares);
    }
}


