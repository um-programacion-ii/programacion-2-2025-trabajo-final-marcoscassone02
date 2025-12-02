package um.edu.ar.proxy.infrastructure.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import um.edu.ar.proxy.domain.model.Asiento;
import um.edu.ar.proxy.domain.model.EstadoAsiento;
import um.edu.ar.proxy.domain.ports.out.AsientosPort;
import um.edu.ar.proxy.infrastructure.redis.dto.RedisAsientosDTO;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RedisAsientosAdapter implements AsientosPort {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public List<Asiento> obtenerAsientosEvento(Long eventoId, int filas, int columnas) {

        String key = "evento_" + eventoId;
        String json = redisTemplate.opsForValue().get(key);

        Map<String, EstadoAsiento> ocupadosOBloqueados = new HashMap<>();

        if (json != null && !json.isBlank()) {
            try {
                RedisAsientosDTO data = objectMapper.readValue(json, RedisAsientosDTO.class);

                // ✔️ Ahora usamos getters
                if (data.getAsientos() != null) {
                    for (RedisAsientosDTO.AsientoRedisDTO a : data.getAsientos()) {

                        String seatKey = a.getFila() + "-" + a.getColumna();
                        ocupadosOBloqueados.put(seatKey, mapEstado(a.getEstado()));
                    }
                }

            } catch (Exception e) {
                System.out.println("Error parseando JSON de Redis para " + key + ": " + e.getMessage());
            }
        }

        List<Asiento> resultado = new ArrayList<>();

        for (int fila = 1; fila <= filas; fila++) {
            for (int columna = 1; columna <= columnas; columna++) {

                String seatKey = fila + "-" + columna;

                EstadoAsiento estado = ocupadosOBloqueados
                        .getOrDefault(seatKey, EstadoAsiento.LIBRE);

                Asiento asiento = new Asiento();
                asiento.setFila(fila);
                asiento.setColumna(columna);
                asiento.setEstado(estado);

                resultado.add(asiento);
            }
        }

        return resultado;
    }

    private EstadoAsiento mapEstado(String estadoRedis) {
        if (estadoRedis == null) {
            return EstadoAsiento.LIBRE;
        }

        return switch (estadoRedis.toUpperCase()) {
            case "BLOQUEADO" -> EstadoAsiento.BLOQUEADO;
            case "VENDIDO", "OCUPADO" -> EstadoAsiento.VENDIDO;
            default -> EstadoAsiento.LIBRE;
        };
    }
}


