package um.edu.ar.proxy.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BackendEventsClient {

    private final WebClient webClientBackend;

    public BackendEventsClient(@Value("${backend.base-url}") String backendBaseUrl) {
        this.webClientBackend = WebClient.builder()
                .baseUrl(backendBaseUrl)
                .build();
    }

    public void notificarSincronizacionEventos() {
        webClientBackend.post()
                .uri("/api/eventos/sincronizar")
                .retrieve()
                .toBodilessEntity()
                .onErrorResume(ex -> {
                    System.out.println("[Kafka→Proxy] Error notificando sincronización al backend: " + ex.getMessage());
                    return Mono.empty();
                })
                .block();
    }
}