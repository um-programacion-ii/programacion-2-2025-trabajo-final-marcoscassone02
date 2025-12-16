package um.edu.ar.backend.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.model.SessionState;
import um.edu.ar.backend.domain.ports.out.SesionService;
import um.edu.ar.backend.infrastructure.persistence.entity.UsuarioApp;
import um.edu.ar.backend.infrastructure.persistence.repository.UsuarioAppRepository;
import um.edu.ar.backend.infrastructure.web.dto.LoginRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/sesion")
@RequiredArgsConstructor
public class SesionController {

    private final SesionService sesionService;
    private final UsuarioAppRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/iniciar")
    public ResponseEntity<Map<String, Object>> iniciar(@RequestBody LoginRequest request) {

        Map<String, Object> r = new HashMap<>();

        UsuarioApp usuario = usuarioRepo.findByUsername(request.username())
                .orElse(null);

        if (usuario == null ||
                !passwordEncoder.matches(request.password(), usuario.getPassword())) {

            r.put("resultado", false);
            r.put("descripcion", "Credenciales inválidas");
            return ResponseEntity.status(401).body(r);
        }

        String sessionId = sesionService.iniciarORecuperarSesion(usuario.getUsername());

        r.put("resultado", true);
        r.put("descripcion", "Sesión iniciada correctamente");
        r.put("sessionId", sessionId);

        log.info("[LOGIN] username={} sessionId={}", usuario.getUsername(), sessionId);

        return ResponseEntity.ok(r);
    }

    @GetMapping("/estado")
    public ResponseEntity<SessionStateResponse> estado(
            @RequestHeader("X-Session-Id") String sessionId
    ) {
        var state = sesionService.obtenerSesion(sessionId);
        if (state == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SessionStateResponse.from(state));
    }

    public record SessionStateResponse(
            String sessionId,
            String username,
            String pasoActual,
            Long eventoId,
            Long ventaId,
            List<AsientoSeleccionadoResponse> asientos
    ) {
        public static SessionStateResponse from(SessionState state) {

            var asientos = state.getAsientos() == null
                    ? List.<AsientoSeleccionadoResponse>of()
                    : state.getAsientos().stream()
                    .map(a -> new AsientoSeleccionadoResponse(a.getFila(), a.getColumna(), a.getPersona()))
                    .toList();

            return new SessionStateResponse(
                    state.getSessionId(),
                    state.getUsername(),
                    state.getPasoActual() != null ? state.getPasoActual().name() : null,
                    state.getEventoId(),
                    state.getVentaId(),
                    asientos
            );
        }
    }

    public record AsientoSeleccionadoResponse(
            int fila,
            int columna,
            String persona
    ) {}

    @PostMapping("/cerrar")
    public ResponseEntity<Void> cerrar(
            @RequestHeader("X-Session-Id") String sessionId
    ) {
        sesionService.invalidarSesion(sessionId);
        log.info("[LOGOUT] sessionId={} invalidada", sessionId);
        return ResponseEntity.ok().build();
    }

}




