package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Seat;
import um.edu.ar.backend.domain.model.UserSession; // <-- Asume que la sesión existe

import java.util.List;

public interface BlockSeatsUseCase {
    UserSession blockSeats(Long eventId, String userId, List<Seat> seatsToBlock);
}
