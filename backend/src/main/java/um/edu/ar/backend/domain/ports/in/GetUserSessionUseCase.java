package um.edu.ar.backend.domain.ports.in;

import um.edu.ar.backend.domain.model.UserSession;

public interface GetUserSessionUseCase {
    UserSession getSession(String sessionId);
}
