package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.SessionState;
import um.edu.ar.backend.domain.ports.out.SesionService;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisSesionService implements SesionService {

    private static final Duration TTL = Duration.ofMinutes(30);

    private final RedisTemplate<String, Object> redisTemplate;

    private String key(String sessionId) {
        return "session:" + sessionId;
    }

    private String userKey(String username) {
        return "user_session:" + username;
    }

    @Override
    public String iniciarORecuperarSesion(String username) {

        String existingSessionId =
                (String) redisTemplate.opsForValue().get(userKey(username));

        if (existingSessionId != null) {
            SessionState state =
                    (SessionState) redisTemplate.opsForValue().get(key(existingSessionId));

            if (state != null) {
                state.setLastActivity(Instant.now());

                redisTemplate.opsForValue().set(key(existingSessionId), state, TTL);
                redisTemplate.expire(userKey(username), TTL);

                return existingSessionId;
            } else {
                // índice colgando
                redisTemplate.delete(userKey(username));
            }
        }

        // crear nueva sesión
        String sessionId = UUID.randomUUID().toString();

        SessionState state = new SessionState();
        state.setSessionId(sessionId);
        state.setUsername(username);
        state.setPasoActual(SessionState.Step.LISTA_EVENTOS);
        state.setLastActivity(Instant.now());

        redisTemplate.opsForValue().set(key(sessionId), state, TTL);
        redisTemplate.opsForValue().set(userKey(username), sessionId, TTL);

        return sessionId;
    }

    @Override
    public boolean validarSesion(String sessionId) {
        String k = key(sessionId);
        SessionState state = (SessionState) redisTemplate.opsForValue().get(k);

        if (state == null) return false;

        redisTemplate.expire(k, TTL);
        redisTemplate.expire(userKey(state.getUsername()), TTL);
        return true;
    }

    @Override
    public SessionState obtenerSesion(String sessionId) {
        return (SessionState) redisTemplate.opsForValue().get(key(sessionId));
    }

    @Override
    public void guardarSesion(SessionState sessionState) {
        sessionState.setLastActivity(Instant.now());

        redisTemplate.opsForValue().set(
                key(sessionState.getSessionId()),
                sessionState,
                TTL
        );

        redisTemplate.opsForValue().set(
                userKey(sessionState.getUsername()),
                sessionState.getSessionId(),
                TTL
        );
    }

    @Override
    public void invalidarSesion(String sessionId) {

        SessionState state =
                (SessionState) redisTemplate.opsForValue().get(key(sessionId));

        if (state != null && state.getUsername() != null) {
            redisTemplate.delete(userKey(state.getUsername()));
        }

        redisTemplate.delete(key(sessionId));
    }
}
