package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.infrastructure.persistence.entity.AsientoBloqueado;

import java.time.Instant;
import java.util.List;

public interface AsientoBloqueadoRepositoryPort {

    List<AsientoBloqueado> findBySessionIdAndEventoId(String sessionId, Long eventoId);

    AsientoBloqueado save(AsientoBloqueado asiento);

    void deleteBySessionId(String sessionId);

    void deleteExpired(Instant ahora);

    void deleteBySessionIdAndEventoId(String sessionId, Long eventoId);

}
