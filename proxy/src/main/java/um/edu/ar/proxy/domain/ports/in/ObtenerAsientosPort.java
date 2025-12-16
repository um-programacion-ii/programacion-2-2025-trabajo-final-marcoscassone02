package um.edu.ar.proxy.domain.ports.in;

import um.edu.ar.proxy.domain.model.Asiento;

import java.util.List;

public interface ObtenerAsientosPort {
    List<Asiento> obtenerAsientosOcupados(Long eventoId);
}
