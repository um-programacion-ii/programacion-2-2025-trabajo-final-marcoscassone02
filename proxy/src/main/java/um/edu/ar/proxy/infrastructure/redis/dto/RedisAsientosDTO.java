package um.edu.ar.proxy.infrastructure.redis.dto;

import lombok.Data;
import java.util.List;

@Data
public class RedisAsientosDTO {

    private Long eventoId;
    private List<AsientoRedisDTO> asientos;

    @Data
    public static class AsientoRedisDTO {
        private int fila;
        private int columna;
        private String estado;
        private String expira;
    }
}