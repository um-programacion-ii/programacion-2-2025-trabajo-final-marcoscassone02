package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.Asiento;

import java.util.List;

public interface AsientosProxyPort {

    List<Asiento> obtenerAsientos(Long eventoId, int filas, int columnas);
}
