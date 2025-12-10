package um.edu.ar.backend.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.edu.ar.backend.infrastructure.persistence.entity.VentaEntity;

import java.util.List;

public interface VentaJpaRepository
        extends JpaRepository<VentaEntity, Long> {

    List<VentaEntity> findByResultado(boolean resultado);
}
