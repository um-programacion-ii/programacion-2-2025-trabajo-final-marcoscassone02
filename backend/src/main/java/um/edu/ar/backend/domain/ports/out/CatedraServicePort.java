package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.Event;
import um.edu.ar.backend.domain.model.Seat;
import um.edu.ar.backend.domain.model.Sale; // Asumiendo que existe el modelo Sale
import um.edu.ar.backend.domain.model.SeatBlockResult;

import java.util.List;

public interface CatedraServicePort {

    List<Event> fetchEventListFromCatedra();
    Event retrieveEventDetails(Long eventId);
    SeatBlockResult blockSeatsInCatedra(Long eventId, List<Seat> seats);
    Sale confirmSaleInCatedra(Long eventId, List<Seat> seats);

}
