package um.edu.ar.proxy.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import um.edu.ar.proxy.infrastructure.web.dto.CatedraVentaRequest;
import um.edu.ar.proxy.infrastructure.web.dto.CatedraVentaResponse;

@Component
public class CatedraVentaClient {

    private final WebClient webClientCatedra;
    private final String catedraAuthToken;

    public CatedraVentaClient(
            @Value("${catedra.base-url}") String catedraBaseUrl,
            @Value("${catedra.auth-token}") String catedraAuthToken
    ) {
        this.webClientCatedra = WebClient.builder()
                .baseUrl(catedraBaseUrl)
                .build();
        this.catedraAuthToken = catedraAuthToken;
    }

    public CatedraVentaResponse realizarVenta(CatedraVentaRequest request) {
        try {
            return webClientCatedra.post()
                    .uri("/api/endpoints/v1/realizar-venta")
                    .header("Authorization", "Bearer " + catedraAuthToken)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(CatedraVentaResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.out.println("ERROR CATEDRA VENTA: status=" + e.getStatusCode());
            System.out.println("BODY: " + e.getResponseBodyAsString());
            throw e;
        }
    }
}