package um.edu.ar.backend.infrastructure.http;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import um.edu.ar.backend.domain.ports.out.ProxyVentaPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProxyVentaAdapter implements ProxyVentaPort {

    private final RestTemplate restTemplate;

    @Value("${proxy.base-url}")
    private String proxyBaseUrl;

    @Override
    public ProxyVentaResultado realizarVenta(
            Long eventoId,
            double precioVenta,
            List<AsientoVentaRequest> asientos
    ) {
        String url = proxyBaseUrl + "/proxy/eventos/" + eventoId + "/venta";
        Map<String, Object> requestBody = new HashMap<>();
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

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<ProxyVentaResultado> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, ProxyVentaResultado.class);

            ProxyVentaResultado body = response.getBody();

            if (body == null) {
                return new ProxyVentaResultado(
                        false,
                        "Respuesta vacía del proxy",
                        eventoId,
                        null,
                        precioVenta,
                        List.of()
                );
            }

            return body;

        } catch (RestClientException e) {
            return new ProxyVentaResultado(
                    false,
                    "Error al llamar al proxy: " + e.getMessage(),
                    eventoId,
                    null,
                    precioVenta,
                    List.of()
            );
        }
    }
}

