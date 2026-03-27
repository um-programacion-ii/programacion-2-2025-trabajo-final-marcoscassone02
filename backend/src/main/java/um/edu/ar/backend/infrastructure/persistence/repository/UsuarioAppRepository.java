package um.edu.ar.backend.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.edu.ar.backend.infrastructure.persistence.entity.UsuarioApp;

import java.util.Optional;

public interface UsuarioAppRepository extends JpaRepository<UsuarioApp, Long> {
    Optional<UsuarioApp> findByUsername(String username);
}
