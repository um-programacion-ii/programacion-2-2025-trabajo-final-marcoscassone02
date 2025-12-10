package um.edu.ar.proxy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.edu.ar.proxy.infrastructure.http.CatedraVentaClient;
import um.edu.ar.proxy.infrastructure.web.dto.CatedraVentaRequest;
import um.edu.ar.proxy.infrastructure.web.dto.CatedraVentaResponse;
import um.edu.ar.proxy.infrastructure.web.dto.VentaRequest;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class VentaProxyService {

    private final CatedraVentaClient catedraVentaClient;

    public CatedraVentaResponse realizarVenta(Long eventoId, VentaRequest ventaRequest) {

        String fecha = (ventaRequest.getFecha() != null && !ventaRequest.getFecha().isBlank())
                ? ventaRequest.getFecha()
                : Instant.now().toString();

        var catedraRequest = new CatedraVentaRequest(
                eventoId,
                fecha,
                ventaRequest.getPrecioVenta(),
                ventaRequest.getAsientos()
        );

        return catedraVentaClient.realizarVenta(catedraRequest);
    }
}
