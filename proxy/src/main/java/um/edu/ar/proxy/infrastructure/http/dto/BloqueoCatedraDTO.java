package um.edu.ar.proxy.infrastructure.http.dto;

import lombok.Data;

import java.util.List;

@Data
public class BloqueoCatedraDTO {

    private boolean resultado;
    private String descripcion;
    private Long eventoId;
    private List<ItemAsientoDTO> asientos;

    @Data
    public static class ItemAsientoDTO {
        private int fila;
        private int columna;
        private String estado;
    }
}
