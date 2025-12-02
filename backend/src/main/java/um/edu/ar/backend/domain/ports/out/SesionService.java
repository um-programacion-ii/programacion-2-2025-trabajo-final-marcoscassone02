package um.edu.ar.backend.domain.ports.out;

public interface SesionService {
    String crearSesion();
    boolean validarSesion(String sessionId);
}
