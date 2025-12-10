package um.edu.ar.backend.application.service;

import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.ports.out.SesionService;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemorySesionService implements SesionService {

    private final Map<String, LocalDateTime> sesiones = new ConcurrentHashMap<>();

    @Override
    public String crearSesion() {

        String sessionId = UUID.randomUUID().toString();
        LocalDateTime expira = LocalDateTime.now().plusMinutes(30);
        sesiones.put(sessionId, expira);

        return sessionId;
    }
    @Override
    public boolean validarSesion(String sessionId) {
        LocalDateTime expira = sesiones.get(sessionId);
        return expira != null && expira.isAfter(LocalDateTime.now());
    }
}

