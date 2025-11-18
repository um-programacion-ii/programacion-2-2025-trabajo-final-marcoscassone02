package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.Seat;

import java.util.List;

public interface ProxyServicePort {

    List<Seat> fetchSeatStatusFromProxy(Long eventId);
}