package um.edu.ar.backend.application.service;

import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.ports.out.SesionTokenService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class InMemorySesionTokenService implements SesionTokenService {

    private final ConcurrentMap<String, String> tokensPorSesion = new ConcurrentHashMap<>();

    @Override
    public void guardarToken(String sessionId, String token) {
        if (sessionId == null || token == null) {
            return;
        }
        tokensPorSesion.put(sessionId, token);
    }

    @Override
    public String obtenerToken(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        return tokensPorSesion.get(sessionId);
    }
}
