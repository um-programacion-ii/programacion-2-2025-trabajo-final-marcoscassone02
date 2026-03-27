package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Evento;

import java.util.List;
import java.util.Optional;

public interface GestionEventosUseCase {

    List<Evento> listarEventos();

    Optional<Evento> obtenerEventoPorId(Long id);
}
