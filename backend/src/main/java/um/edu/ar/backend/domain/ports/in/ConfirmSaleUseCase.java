package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.Sale;
import um.edu.ar.backend.domain.model.Seat;

import java.util.List;

public interface ConfirmSaleUseCase {
    Sale confirmSale(Long eventId, Long clienteId, List<Seat> seatsToConfirm);
}
