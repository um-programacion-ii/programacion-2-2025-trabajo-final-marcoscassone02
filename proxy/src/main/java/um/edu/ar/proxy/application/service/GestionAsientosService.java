package um.edu.ar.proxy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.edu.ar.proxy.domain.model.Asiento;
import um.edu.ar.proxy.domain.model.BloquearAsientosCommand;
import um.edu.ar.proxy.domain.model.BloqueoResultado;
import um.edu.ar.proxy.domain.ports.in.BloquearAsientosPort;
import um.edu.ar.proxy.domain.ports.in.ObtenerAsientosPort;
import um.edu.ar.proxy.domain.ports.out.CatedraAsientosPort;
import um.edu.ar.proxy.domain.ports.out.RedisAsientosPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestionAsientosService implements ObtenerAsientosPort, BloquearAsientosPort {

    private final RedisAsientosPort redisAsientosPort;
    private final CatedraAsientosPort catedraAsientosPort;

    @Override
    public List<Asiento> obtenerAsientosOcupados(Long eventoId) {
        return redisAsientosPort.obtenerAsientosOcupados(eventoId);
    }

    @Override
    public BloqueoResultado bloquearAsientos(BloquearAsientosCommand command) {
        return catedraAsientosPort.bloquearAsientos(command);
    }
}

