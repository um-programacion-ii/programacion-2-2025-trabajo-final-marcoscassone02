package um.edu.ar.backend.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import um.edu.ar.backend.domain.model.Asiento;
import um.edu.ar.backend.domain.model.EstadoAsiento;
import um.edu.ar.backend.domain.ports.out.AsientosProxyPort;
import um.edu.ar.backend.infrastructure.http.dto.ProxyAsientosResponse;

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

        ProxyAsientosResponse proxyResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/proxy/eventos/{id}/asientos")
                        .queryParam("filas", filas)
                        .queryParam("columnas", columnas)
                        .build(eventoId))
                .retrieve()
                .bodyToMono(ProxyAsientosResponse.class)
                .block();

        if (proxyResponse == null || proxyResponse.getAsientos() == null) {
            return List.of();
        }

        return proxyResponse.getAsientos().stream()
                .map(dto -> new Asiento(
                        dto.getFila(),
                        dto.getColumna(),
                        convertirEstado(dto.getEstado())
                ))
                .toList();
    }

    private EstadoAsiento convertirEstado(String estado) {

        return switch (estado.toUpperCase()) {
            case "BLOQUEADO" -> EstadoAsiento.BLOQUEADO;
            case "VENDIDO"   -> EstadoAsiento.VENDIDO;
            default          -> EstadoAsiento.LIBRE;
        };
    }
}


