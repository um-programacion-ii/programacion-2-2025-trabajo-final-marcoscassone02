package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.SessionState;
import um.edu.ar.backend.domain.ports.out.SesionService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisSesionService implements SesionService {

    private static final Duration TTL = Duration.ofMinutes(30);

    private final RedisTemplate<String, Object> redisTemplate;

    private String key(String sessionId) {
        return "session:" + sessionId;
    }

    @Override
    public String crearSesion() {

        String sessionId = java.util.UUID.randomUUID().toString();

        SessionState state = new SessionState();
        state.setSessionId(sessionId);
        state.setPasoActual(SessionState.Step.LISTA_EVENTOS);
        state.setLastActivity(java.time.Instant.now());

        redisTemplate.opsForValue().set(
                key(sessionId),
                state,
                TTL
        );

        return sessionId;
    }

    @Override
    public boolean validarSesion(String sessionId) {
        String key = key(sessionId);
        SessionState state = (SessionState) redisTemplate.opsForValue().get(key);
        if (state == null) {
            return false;
        }

        redisTemplate.expire(key, TTL);
        return true;
    }

    @Override
    public SessionState obtenerSesion(String sessionId) {
        return (SessionState) redisTemplate.opsForValue().get(key(sessionId));
    }

    @Override
    public void guardarSesion(SessionState sessionState) {
        sessionState.setLastActivity(java.time.Instant.now());
        redisTemplate.opsForValue().set(
                key(sessionState.getSessionId()),
                sessionState,
                TTL
        );
    }

    @Override
    public void invalidarSesion(String sessionId) {
        redisTemplate.delete(key(sessionId));
    }
}