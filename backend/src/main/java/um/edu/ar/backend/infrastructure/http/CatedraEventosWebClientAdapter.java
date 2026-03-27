package um.edu.ar.backend.infrastructure.http;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import um.edu.ar.backend.domain.model.Evento;
import um.edu.ar.backend.domain.model.TipoEvento;
import um.edu.ar.backend.domain.ports.out.EventosRemotosPort;
import um.edu.ar.backend.infrastructure.http.dto.CatedraEventoDTO;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CatedraEventosWebClientAdapter implements EventosRemotosPort {

    private final WebClient catedraWebClient;

    @Override
    public List<Evento> obtenerEventosDesdeServidorCatedra() {

        List<CatedraEventoDTO> eventosRemotos = catedraWebClient.get()
                .uri("/api/endpoints/v1/eventos")
                .retrieve()
                .bodyToFlux(CatedraEventoDTO.class)
                .collectList()
                .block();

        if (eventosRemotos == null) {
            return List.of();
        }

        return eventosRemotos.stream()
                .map(this::toDomain)
                .toList();
    }

    private Evento toDomain(CatedraEventoDTO dto) {

        TipoEvento tipo = null;
        if (dto.getEventoTipo() != null) {
            tipo = new TipoEvento(
                    null,
                    dto.getEventoTipo().getNombre(),
                    dto.getEventoTipo().getDescripcion()
            );
        }

        LocalDateTime fechaHora = null;
        if (dto.getFecha() != null) {
            fechaHora = OffsetDateTime.parse(dto.getFecha()).toLocalDateTime();
        }

        return new Evento(
                dto.getId(),
                dto.getTitulo(),
                dto.getResumen(),
                dto.getDescripcion(),
                fechaHora,
                dto.getDireccion(),
                tipo,
                dto.getFilaAsientos(),
                dto.getColumnAsientos(),
                dto.getPrecioEntrada()
        );
    }
}
