package um.edu.ar.proxy.infrastructure.redis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedisAsientosDTO {

    private Long eventoId;
    private List<ItemAsientoDTO> asientos;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ItemAsientoDTO {
        private int fila;
        private int columna;
        private String estado;
        private String expira;
    }
}
