package um.edu.ar.backend.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.edu.ar.backend.infrastructure.persistence.entity.AsientoBloqueado;

import java.time.Instant;
import java.util.List;

public interface JpaAsientoBloqueadoRepository extends JpaRepository<AsientoBloqueado, Long> {

    List<AsientoBloqueado> findBySessionIdAndEventoId(String sessionId, Long eventoId);

    void deleteBySessionId(String sessionId);

    void deleteByExpiracionBefore(Instant expiracion);
}
