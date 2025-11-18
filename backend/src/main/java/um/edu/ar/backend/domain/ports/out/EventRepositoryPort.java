package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.Event;

import java.util.List;

public interface EventRepositoryPort {
    Event save(Event event);
    Event findById(Long id);
    List<Event> findAll();
}
