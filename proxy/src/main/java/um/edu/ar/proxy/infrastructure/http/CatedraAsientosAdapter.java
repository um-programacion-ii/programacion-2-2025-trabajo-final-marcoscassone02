package um.edu.ar.proxy.infrastructure.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import um.edu.ar.proxy.domain.model.Asiento;
import um.edu.ar.proxy.domain.model.BloquearAsientosCommand;
import um.edu.ar.proxy.domain.model.BloqueoResultado;
import um.edu.ar.proxy.domain.model.EstadoAsiento;
import um.edu.ar.proxy.domain.ports.out.CatedraAsientosPort;
import um.edu.ar.proxy.infrastructure.http.dto.BloqueoCatedraDTO;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatedraAsientosAdapter implements CatedraAsientosPort {

    private final WebClient catedraWebClient; // ya viene con Authorization configurado

    @Override
    public BloqueoResultado bloquearAsientos(BloquearAsientosCommand command) {

        var requestBody = Map.of(
                "eventoId", command.getEventoId(),
                "asientos", command.getAsientos().stream()
                        .map(a -> Map.of("fila", a.fila(), "columna", a.columna()))
                        .toList()
        );

        log.info("[Cátedra][REQ] Body: {}", requestBody);

        var response = catedraWebClient.post()
                .uri("/api/endpoints/v1/bloquear-asientos")
                .bodyValue(requestBody)  // ya NO seteamos header Authorization acá
                .retrieve()
                .bodyToMono(BloqueoCatedraDTO.class)
                .doOnNext(r -> log.info("[Cátedra][RESP RAW] {}", r))
                .block();

        return mapToDomain(response);
    }

    private BloqueoResultado mapToDomain(BloqueoCatedraDTO dto) {
        List<Asiento> asientos = dto.getAsientos().stream()
                .map(a -> new Asiento(
                        a.getFila(),
                        a.getColumna(),
                        "Ocupado".equalsIgnoreCase(a.getEstado())
                                ? EstadoAsiento.VENDIDO
                                : EstadoAsiento.BLOQUEADO,
                        null
                ))
                .toList();

        return new BloqueoResultado(dto.isResultado(), dto.getDescripcion(), dto.getEventoId(), asientos);
    }
}
