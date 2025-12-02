package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.Asiento;
import um.edu.ar.backend.domain.ports.in.GestionAsientosUseCase;
import um.edu.ar.backend.domain.ports.out.AsientosProxyPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestionAsientosService implements GestionAsientosUseCase {

    private final AsientosProxyPort asientosProxyPort;

    @Override
    public List<Asiento> obtenerAsientosEvento(Long eventoId, int filas, int columnas) {
        return asientosProxyPort.obtenerAsientos(eventoId, filas, columnas);
    }
}
