package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.edu.ar.backend.domain.model.Venta;
import um.edu.ar.backend.domain.ports.in.RealizarVentaUseCase;
import um.edu.ar.backend.domain.ports.out.AsientoBloqueadoRepositoryPort;
import um.edu.ar.backend.domain.ports.out.CatedraVentaPort;
import um.edu.ar.backend.domain.ports.out.SesionService;
import um.edu.ar.backend.domain.ports.out.VentaRepositoryPort;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RealizarVentaService implements RealizarVentaUseCase {

    private final SesionService sesionService;
    private final CatedraVentaPort proxyVentaPort;
    private final AsientoBloqueadoRepositoryPort asientoBloqueadoRepository;
    private final VentaRepositoryPort ventaRepository;

    @Override
    @Transactional
    public RealizarVentaResponse realizarVenta(RealizarVentaCommand command) {

        if (!sesionService.validarSesion(command.sessionId())) {
            return new RealizarVentaResponse(
                    false,
                    "Sesión inválida o expirada",
                    command.eventoId(),
                    null,
                    command.precioVenta(),
                    List.of()
            );
        }

        asientoBloqueadoRepository.deleteExpired(Instant.now());

        var bloqueos = asientoBloqueadoRepository.findBySessionIdAndEventoId(
                command.sessionId(),
                command.eventoId()
        );

        Set<String> bloqueadosSet = bloqueos.stream()
                .map(b -> b.getFila() + ":" + b.getColumna())
                .collect(Collectors.toSet());

        var asientosSolicitados = command.asientos();

        var asientosNoBloqueados = asientosSolicitados.stream()
                .filter(a -> !bloqueadosSet.contains(a.fila() + ":" + a.columna()))
                .toList();

        if (!asientosNoBloqueados.isEmpty()) {

            Venta ventaFallida = VentaFactory.fromBloqueoFallido(command, asientosNoBloqueados);
            ventaRepository.save(ventaFallida);

            String detalle = asientosNoBloqueados.stream()
                    .map(a -> "(" + a.fila() + "," + a.columna() + ")")
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");

            return new RealizarVentaResponse(
                    false,
                    "Algunos asientos no están bloqueados para esta sesión o el bloqueo expiró: " + detalle,
                    command.eventoId(),
                    null,
                    command.precioVenta(),
                    List.of()
            );
        }

        var asientosReq = asientosSolicitados.stream()
                .map(a -> new CatedraVentaPort.AsientoVentaRequest(
                        a.fila(), a.columna(), a.persona()
                ))
                .toList();

        var resultado = proxyVentaPort.realizarVenta(
                command.eventoId(),
                command.precioVenta(),
                asientosReq
        );

        if (!resultado.resultado()) {

            Venta ventaFallida = VentaFactory.fromFallido(command, resultado);
            ventaRepository.save(ventaFallida);

            var asientosRespError = resultado.asientos().stream()
                    .map(a -> new RealizarVentaResponse.AsientoEstado(
                            a.fila(), a.columna(), a.persona(), a.estado()
                    ))
                    .toList();

            return new RealizarVentaResponse(
                    false,
                    resultado.descripcion(),
                    resultado.eventoId(),
                    resultado.ventaId(),
                    resultado.precioVenta(),
                    asientosRespError
            );
        }

        asientoBloqueadoRepository.deleteBySessionId(command.sessionId());

        Venta ventaExitosa = VentaFactory.fromExitoso(command, resultado);
        ventaRepository.save(ventaExitosa);

        var asientosResp = resultado.asientos().stream()
                .map(a -> new RealizarVentaResponse.AsientoEstado(
                        a.fila(), a.columna(), a.persona(), a.estado()
                ))
                .toList();

        return new RealizarVentaResponse(
                true,
                resultado.descripcion(),
                resultado.eventoId(),
                resultado.ventaId(),
                resultado.precioVenta(),
                asientosResp
        );
    }
}


