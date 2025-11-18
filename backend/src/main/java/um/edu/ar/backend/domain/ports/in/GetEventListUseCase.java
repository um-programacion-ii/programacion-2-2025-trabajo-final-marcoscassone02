package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Event;

import java.util.List;

public interface GetEventListUseCase {
    List<Event> getEventList();
}
