package um.edu.ar.backend.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.edu.ar.backend.infrastructure.persistence.entity.EventoEntity;

public interface JpaEventoRepository extends JpaRepository<EventoEntity, Long> {
}
