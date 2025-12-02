package um.edu.ar.proxy.domain.ports.out;

import um.edu.ar.proxy.domain.model.Asiento;

import java.util.List;

public interface AsientosPort {

    List<Asiento> obtenerAsientosEvento(Long eventoId, int filas, int columnas);
}
