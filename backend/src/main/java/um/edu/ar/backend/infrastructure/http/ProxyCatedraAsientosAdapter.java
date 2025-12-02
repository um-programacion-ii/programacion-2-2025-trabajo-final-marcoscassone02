package um.edu.ar.backend.infrastructure.http;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import um.edu.ar.backend.domain.model.AsientoId;
import um.edu.ar.backend.domain.ports.out.CatedraAsientosPort;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProxyCatedraAsientosAdapter implements CatedraAsientosPort {

    private final RestTemplate restTemplate;

    @Value("${proxy.base-url}")
    private String proxyBaseUrl;

    @Override
    public BloqueoResultado bloquearAsientos(
            Long eventoId,
            List<AsientoId> asientos,
            String authorizationHeader
    ) {
        String url = proxyBaseUrl + "/api/catedra/eventos/" + eventoId + "/bloquear-asientos";

        BloquearAsientosProxyRequest request = new BloquearAsientosProxyRequest(
                asientos.stream()
                        .map(a -> new AsientoRequest(a.fila(), a.columna()))
                        .toList()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorizationHeader); // ⬅ reenviamos el token

        HttpEntity<BloquearAsientosProxyRequest> entity =
                new HttpEntity<>(request, headers);

        try {
            ResponseEntity<BloqueoResultado> responseEntity =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            BloqueoResultado.class
                    );

            BloqueoResultado body = responseEntity.getBody();

            if (body == null) {
                return new BloqueoResultado(
                        false,
                        "Respuesta vacía del proxy",
                        eventoId,
                        List.of()
                );
            }

            return body;

        } catch (RestClientException e) {
            return new BloqueoResultado(
                    false,
                    "Error al llamar al proxy: " + e.getMessage(),
                    eventoId,
                    List.of()
            );
        }
    }

    // ==== DTO que el backend envía al PROXY ====

    public record BloquearAsientosProxyRequest(
            List<AsientoRequest> asientos
    ) {}

    public record AsientoRequest(
            int fila,
            int columna
    ) {}
}





