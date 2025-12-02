package um.edu.ar.backend.domain.ports.out;


import um.edu.ar.backend.domain.model.Evento;

import java.util.List;
import java.util.Optional;

public interface EventoRepository {

    List<Evento> findAll();
    Optional<Evento> findById(Long id);
    Evento save(Evento evento);
    void saveAll(List<Evento> eventos);
}
