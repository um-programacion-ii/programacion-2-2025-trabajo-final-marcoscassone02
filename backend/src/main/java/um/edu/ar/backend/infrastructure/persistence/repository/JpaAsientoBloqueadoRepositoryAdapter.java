package um.edu.ar.backend.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.edu.ar.backend.domain.ports.out.AsientoBloqueadoRepositoryPort;
import um.edu.ar.backend.infrastructure.persistence.entity.AsientoBloqueado;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaAsientoBloqueadoRepositoryAdapter implements AsientoBloqueadoRepositoryPort {

    private final JpaAsientoBloqueadoRepository jpaRepository;

    @Override
    public List<AsientoBloqueado> findBySessionIdAndEventoId(String sessionId, Long eventoId) {
        return jpaRepository.findBySessionIdAndEventoId(sessionId, eventoId);
    }

    @Override
    public AsientoBloqueado save(AsientoBloqueado asiento) {
        return jpaRepository.save(asiento);
    }

    @Override
    public void deleteBySessionId(String sessionId) {
        jpaRepository.deleteBySessionId(sessionId);
    }

    @Override
    public void deleteExpired(Instant ahora) {
        jpaRepository.deleteByExpiracionBefore(ahora);
    }

    @Override
    public void deleteBySessionIdAndEventoId(String sessionId, Long eventoId) {
        jpaRepository.deleteBySessionIdAndEventoId(sessionId, eventoId);
    }
}


