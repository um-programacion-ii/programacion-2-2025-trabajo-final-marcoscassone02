package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Seat;

import java.util.List;

public interface GetSeatMapUseCase {
    List<Seat> getSeatMap(Long eventId);
}
