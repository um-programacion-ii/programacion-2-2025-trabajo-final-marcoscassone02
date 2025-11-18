package um.edu.ar.backend.application.service;

import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.Seat;
import um.edu.ar.backend.domain.model.UserSession;
import um.edu.ar.backend.domain.ports.in.BlockSeatsUseCase;
import um.edu.ar.backend.domain.ports.out.CatedraServicePort;
import um.edu.ar.backend.domain.ports.out.SessionRepositoryPort;

import java.util.List;


@Service
public class SeatBlockService implements BlockSeatsUseCase {

    private final CatedraServicePort catedraServicePort;
    private final SessionRepositoryPort sessionRepositoryPort;

    public SeatBlockService(CatedraServicePort catedraServicePort, SessionRepositoryPort sessionRepositoryPort) {
        this.catedraServicePort = catedraServicePort;
        this.sessionRepositoryPort = sessionRepositoryPort;
    }

    @Override
    public UserSession blockSeats(Long eventId, String userId, List<Seat> seatsToBlock) {
        return null;
    }
}
