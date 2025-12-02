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
        String id = UUID.randomUUID().toString();
        sesiones.put(id, LocalDateTime.now().plusMinutes(30));
        return id;
    }

    @Override
    public boolean validarSesion(String sessionId) {
        LocalDateTime expira = sesiones.get(sessionId);
        return expira != null && expira.isAfter(LocalDateTime.now());
    }
}
