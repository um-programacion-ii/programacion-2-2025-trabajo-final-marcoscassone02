package um.edu.ar.proxy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import um.edu.ar.proxy.domain.model.Asiento;
import um.edu.ar.proxy.domain.ports.out.AsientosPort;
import um.edu.ar.proxy.infrastructure.web.dto.BloquearAsientosRequest;
import um.edu.ar.proxy.infrastructure.web.dto.BloqueoAsientosResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestionAsientosService {

    private final AsientosPort asientosPort;
    private final RestTemplate restTemplate;

    @Value("${catedra.base-url}")
    private String catedraBaseUrl;

    public List<Asiento> obtenerMapaAsientos(Long eventoId, int filas, int columnas) {
        return asientosPort.obtenerAsientosEvento(eventoId, filas, columnas);
    }

    public BloqueoAsientosResponse bloquearAsientos(
            Long eventoId,
            BloquearAsientosRequest request,
            String authorizationHeader
    ) {
        String url = catedraBaseUrl + "/api/endpoints/v1/bloquear-asientos";

        CatedraBloquearAsientosRequest payload = new CatedraBloquearAsientosRequest(
                eventoId,
                request.asientos().stream()
                        .map(a -> new CatedraAsientoRequest(a.fila(), a.columna()))
                        .toList()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorizationHeader);

        HttpEntity<CatedraBloquearAsientosRequest> entity =
                new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<BloqueoAsientosResponse> responseEntity =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            BloqueoAsientosResponse.class
                    );

            BloqueoAsientosResponse body = responseEntity.getBody();

            if (body == null) {
                return new BloqueoAsientosResponse(
                        false,
                        "Respuesta vacía del servidor de la cátedra",
                        eventoId,
                        List.of()
                );
            }

            return body;

        } catch (RestClientException e) {
            return new BloqueoAsientosResponse(
                    false,
                    "Error llamando al servidor de la cátedra: " + e.getMessage(),
                    eventoId,
                    List.of()
            );
        }
    }

    private record CatedraBloquearAsientosRequest(
            Long eventoId,
            List<CatedraAsientoRequest> asientos
    ) {}

    private record CatedraAsientoRequest(
            int fila,
            int columna
    ) {}
}


