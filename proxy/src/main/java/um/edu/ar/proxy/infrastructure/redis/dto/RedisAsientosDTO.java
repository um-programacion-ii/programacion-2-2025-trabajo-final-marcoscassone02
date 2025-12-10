package um.edu.ar.proxy.infrastructure.redis.dto;

import lombok.Data;
import java.util.List;

@Data
public class RedisAsientosDTO {
    private Long eventoId;
    private List<ItemAsientoDTO> asientos;

    @Data
    public static class ItemAsientoDTO {
        private int fila;
        private int columna;
        private String estado;
        private String expira;
    }
}