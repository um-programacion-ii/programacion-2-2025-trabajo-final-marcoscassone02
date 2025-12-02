package um.edu.ar.backend.infrastructure.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import um.edu.ar.backend.domain.model.Evento;
import um.edu.ar.backend.domain.model.TipoEvento;
import um.edu.ar.backend.domain.ports.out.EventosRemotosPort;
import um.edu.ar.backend.infrastructure.http.dto.AuthRequest;
import um.edu.ar.backend.infrastructure.http.dto.AuthResponse;
import um.edu.ar.backend.infrastructure.http.dto.CatedraEventoDTO;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CatedraEventosWebClientAdapter implements EventosRemotosPort {

    private final WebClient catedraWebClient;

    private String jwtToken;

    private String authenticate() {

        if (jwtToken != null) {
            return jwtToken;
        }

        AuthRequest request = new AuthRequest("admin", "admin", false);

        AuthResponse response = catedraWebClient.post()
                .uri("/api/authenticate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();

        if (response == null || response.getId_token() == null) {
            throw new IllegalStateException("No se pudo obtener el token JWT desde la cátedra");
        }

        this.jwtToken = response.getId_token();
        return this.jwtToken;
    }

    @Override
    public List<Evento> obtenerEventosDesdeServidorCatedra() {

        String token = authenticate();

        List<CatedraEventoDTO> eventosRemotos = catedraWebClient.get()
                .uri("/api/endpoints/v1/eventos")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
