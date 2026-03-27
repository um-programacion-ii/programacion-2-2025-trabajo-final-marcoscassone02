package um.edu.ar.backend.domain.ports.out;

import um.edu.ar.backend.domain.model.SessionState;

public interface SesionService {

    String iniciarORecuperarSesion(String username);
    boolean validarSesion(String sessionId);
    SessionState obtenerSesion(String sessionId);
    void guardarSesion(SessionState sessionState);
    void invalidarSesion(String sessionId);
}

