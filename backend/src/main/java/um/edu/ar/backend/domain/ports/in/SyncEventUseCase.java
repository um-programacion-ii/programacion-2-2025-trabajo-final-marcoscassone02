package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Event;

public interface SyncEventUseCase {
    Event syncEvent(Long eventId);
}
