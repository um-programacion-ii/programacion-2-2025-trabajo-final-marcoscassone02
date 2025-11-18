package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.UserSession;

import java.util.Optional;

public interface SessionRepositoryPort { // Redis Local
    void save(UserSession session);
    Optional<UserSession> findById(String sessionId);
    void deleteById(String sessionId);
}
