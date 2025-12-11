package um.edu.ar.backend.infrastructure.http.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import um.edu.ar.backend.infrastructure.web.dto.EventoDetalleResponse;

@Component
@RequiredArgsConstructor
public class CatedraEventoClient {

    private final WebClient catedraWebClient;

    public EventoDetalleResponse obtenerDetalleEvento(Long id) {
        return catedraWebClient.get()
                .uri("/api/endpoints/v1/evento/{id}", id)
                .retrieve()
                .bodyToMono(EventoDetalleResponse.class)
                .block();
    }
}
