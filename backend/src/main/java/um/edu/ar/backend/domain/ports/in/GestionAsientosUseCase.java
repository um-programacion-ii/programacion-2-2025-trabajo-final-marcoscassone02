package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Asiento;

import java.util.List;

public interface GestionAsientosUseCase {

    List<Asiento> obtenerAsientosEvento(Long eventoId, int filas, int columnas);
}
