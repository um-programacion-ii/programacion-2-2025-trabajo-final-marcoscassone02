package um.edu.ar.backend.domain.ports.out;

public interface SesionTokenService {

    void guardarToken(String sessionId, String token);

    String obtenerToken(String sessionId);
}
