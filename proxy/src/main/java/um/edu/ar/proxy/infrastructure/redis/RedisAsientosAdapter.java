package um.edu.ar.proxy.infrastructure.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import um.edu.ar.proxy.domain.model.Asiento;
import um.edu.ar.proxy.domain.model.EstadoAsiento;
import um.edu.ar.proxy.domain.ports.out.RedisAsientosPort;
import um.edu.ar.proxy.infrastructure.redis.dto.RedisAsientosDTO;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisAsientosAdapter implements RedisAsientosPort {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public List<Asiento> obtenerAsientosOcupados(Long eventoId) {

        String key = "evento_" + eventoId;
        String json = redisTemplate.opsForValue().get(key);

        if (json == null) {
            return List.of();
        }

        try {
            RedisAsientosDTO dto = objectMapper.readValue(json, RedisAsientosDTO.class);

            return dto.getAsientos().stream()
                    .map(this::mapToDomain)
                    .toList();

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error parseando JSON de Redis para evento " + eventoId, e);
        }
    }

    private Asiento mapToDomain(RedisAsientosDTO.ItemAsientoDTO a) {
        EstadoAsiento estado =
                "Vendido".equalsIgnoreCase(a.getEstado())
                        ? EstadoAsiento.VENDIDO
                        : EstadoAsiento.BLOQUEADO;

        Instant expira = null;
        if (a.getExpira() != null) {
            expira = Instant.parse(a.getExpira());
        }

        return new Asiento(a.getFila(), a.getColumna(), estado, expira);
    }
}
