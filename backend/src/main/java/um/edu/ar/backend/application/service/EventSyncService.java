package um.edu.ar.backend.application.service;

import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.Event;
import um.edu.ar.backend.domain.ports.in.SyncEventUseCase;
import um.edu.ar.backend.domain.ports.out.CatedraServicePort;
import um.edu.ar.backend.domain.ports.out.EventRepositoryPort;


@Service
public class EventSyncService implements SyncEventUseCase {

    private final CatedraServicePort catedraServicePort;
    private final EventRepositoryPort eventRepositoryPort;

    public EventSyncService(CatedraServicePort catedraPort, EventRepositoryPort eventRepo) {
        this.catedraServicePort = catedraPort;
        this.eventRepositoryPort = eventRepo;
    }

    @Override
    public Event syncEvent(Long eventId) {
        Event updatedEvent = catedraServicePort.retrieveEventDetails(eventId);
        return eventRepositoryPort.save(updatedEvent);
    }
}
