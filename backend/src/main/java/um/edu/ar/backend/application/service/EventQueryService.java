package um.edu.ar.backend.application.service;

import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.Event;
import um.edu.ar.backend.domain.ports.in.GetEventListUseCase;
import um.edu.ar.backend.domain.ports.in.GetEventDetailsUseCase;
import um.edu.ar.backend.domain.ports.out.EventRepositoryPort;

import java.util.List;

@Service
public class EventQueryService implements GetEventListUseCase, GetEventDetailsUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public EventQueryService(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public Event getEventDetails(Long eventId) {
        return null;
    }

    @Override
    public List<Event> getEventList() {
        return List.of();
    }
}