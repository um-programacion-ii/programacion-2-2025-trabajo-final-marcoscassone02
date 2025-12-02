package um.edu.ar.backend.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import um.edu.ar.backend.domain.model.Asiento;
import um.edu.ar.backend.domain.ports.out.AsientosProxyPort;

import java.util.List;

@Component
public class ProxyAsientosWebClientAdapter implements AsientosProxyPort {

    private final WebClient webClient;

    public ProxyAsientosWebClientAdapter(@Value("${proxy.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public List<Asiento> obtenerAsientos(Long eventoId, int filas, int columnas) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/proxy/eventos/{id}/asientos")
                        .queryParam("filas", filas)
                        .queryParam("columnas", columnas)
                        .build(eventoId))
                .retrieve()
                .bodyToFlux(Asiento.class)
                .collectList()
                .block();
    }
}


