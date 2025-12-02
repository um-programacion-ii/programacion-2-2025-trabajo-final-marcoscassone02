package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.Evento;

import java.util.List;

public interface EventosRemotosPort {

    List<Evento> obtenerEventosDesdeServidorCatedra();
}
