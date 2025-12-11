package um.edu.ar.backend.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.edu.ar.backend.infrastructure.persistence.entity.VentaEntity;

import java.util.Optional;

public interface VentaJpaRepository
        extends JpaRepository<VentaEntity, Long> {

    Optional<VentaEntity> findByVentaIdCatedra(Long ventaIdCatedra);
}
