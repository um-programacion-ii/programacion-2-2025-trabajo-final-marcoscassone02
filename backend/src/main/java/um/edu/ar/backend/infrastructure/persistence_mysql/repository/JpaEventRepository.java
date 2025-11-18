package um.edu.ar.backend.infrastructure.persistence_mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.edu.ar.backend.infrastructure.persistence_mysql.entity.EventEntity;

@Repository
public interface JpaEventRepository extends JpaRepository<EventEntity, Long> {
}
