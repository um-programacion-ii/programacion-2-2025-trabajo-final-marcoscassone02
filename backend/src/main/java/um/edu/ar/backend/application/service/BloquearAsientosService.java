package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.edu.ar.backend.domain.model.AsientoId;
import um.edu.ar.backend.domain.ports.in.BloquearAsientosUseCase;
import um.edu.ar.backend.domain.ports.out.AsientoBloqueadoRepositoryPort;
import um.edu.ar.backend.domain.ports.out.ProxyBloqueoAsientosPort;
import um.edu.ar.backend.domain.ports.out.SesionService;
import um.edu.ar.backend.infrastructure.persistence.entity.AsientoBloqueado;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BloquearAsientosService implements BloquearAsientosUseCase {

    private final ProxyBloqueoAsientosPort proxyBloqueoAsientosPort;
    private final AsientoBloqueadoRepositoryPort asientoRepo;
    private final SesionService sesionService;   // ⚠️ ahora usamos SesionService, no SesionTokenService

    private static final int MAX_ASIENTOS_POR_SESION = 4;
    private static final int BLOQUEO_MINUTOS = 5;

    @Override
    @Transactional
    public BloquearAsientosResponse bloquear(BloquearAsientosCommand command) {

        asientoRepo.deleteExpired(Instant.now());

        if (!sesionService.validarSesion(command.sessionId())) {
            return new BloquearAsientosResponse(
                    false,
                    "Sesión no válida o expirada",
                    command.eventoId(),
                    List.of()
            );
        }

        var existentes = asientoRepo.findBySessionIdAndEventoId(
                command.sessionId(), command.eventoId());

        int yaBloqueados = existentes.size();
        int nuevos = command.asientos().size();

        if (yaBloqueados + nuevos > MAX_ASIENTOS_POR_SESION) {
            return new BloquearAsientosResponse(
                    false,
                    "No se pueden bloquear más de " + MAX_ASIENTOS_POR_SESION + " asientos por sesión",
                    command.eventoId(),
                    List.of()
            );
        }

        var asientosIds = command.asientos().stream()
                .map(a -> new AsientoId(a.fila(), a.columna()))
                .toList();

        var resultadoCatedra =
                proxyBloqueoAsientosPort.bloquearAsientos(command.eventoId(), asientosIds);

        if (!resultadoCatedra.resultado()) {
            var asientosEstado = resultadoCatedra.asientos().stream()
                    .map(a -> new BloquearAsientosResponse.AsientoEstado(
                            a.fila(), a.columna(), a.estado()))
                    .toList();

            return new BloquearAsientosResponse(
                    false,
                    resultadoCatedra.descripcion(),
                    resultadoCatedra.eventoId(),
                    asientosEstado
            );
        }

        Instant expiracion = Instant.now().plus(BLOQUEO_MINUTOS, ChronoUnit.MINUTES);

        for (var asiento : resultadoCatedra.asientos()) {
            String estado = asiento.estado();
            if ("BLOQUEADO".equalsIgnoreCase(estado)) {
                var bloqueado = new AsientoBloqueado();
                bloqueado.setSessionId(command.sessionId());
                bloqueado.setEventoId(resultadoCatedra.eventoId());
                bloqueado.setFila(asiento.fila());
                bloqueado.setColumna(asiento.columna());
                bloqueado.setExpiracion(expiracion);
                asientoRepo.save(bloqueado);
            }
        }

        var asientosEstado = resultadoCatedra.asientos().stream()
                .map(a -> new BloquearAsientosResponse.AsientoEstado(
                        a.fila(), a.columna(), a.estado()))
                .toList();

        return new BloquearAsientosResponse(
                true,
                resultadoCatedra.descripcion(),
                resultadoCatedra.eventoId(),
                asientosEstado
        );
    }
}





