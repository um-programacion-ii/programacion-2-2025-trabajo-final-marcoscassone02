package um.edu.ar.backend.infrastructure.http;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import um.edu.ar.backend.domain.ports.out.CatedraVentaPort;


import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CatedraVentaAdapter implements CatedraVentaPort {

    private final RestTemplate restTemplate;

    @Value("${catedra.base-url}")
    private String catedraBaseUrl;

    @Value("${catedra.auth-token}")
    private String catedraAuthToken;

    @Override
    public VentaResultado realizarVenta(
            Long eventoId,
            double precioVenta,
            List<AsientoVentaRequest> asientos
    ) {
        String url = catedraBaseUrl + "/api/endpoints/v1/realizar-venta";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("eventoId", eventoId);

        requestBody.put("fecha", Instant.now().toString());

        requestBody.put("precioVenta", precioVenta);
        requestBody.put("asientos", asientos == null
                ? List.of()
                : asientos.stream()
                .map(a -> Map.of(
                        "fila", a.fila(),
                        "columna", a.columna(),
                        "persona", a.persona()
                ))
                .toList()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(catedraAuthToken);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<VentaResultado> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, VentaResultado.class);

            VentaResultado body = response.getBody();

            if (body == null) {
                return new VentaResultado(
                        false,
                        "Respuesta vacía de la cátedra",
                        eventoId,
                        null,
                        precioVenta,
                        List.of()
                );
            }

            return body;
        } catch (RestClientException e) {
            return new VentaResultado(
                    false,
                    "Error al llamar a la cátedra: " + e.getMessage(),
                    eventoId,
                    null,
                    precioVenta,
                    List.of()
            );
        }
    }
}