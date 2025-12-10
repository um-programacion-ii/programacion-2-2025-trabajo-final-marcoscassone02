package um.edu.ar.backend.infrastructure.web.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import um.edu.ar.backend.domain.ports.out.SesionService;
import um.edu.ar.backend.infrastructure.persistence.entity.UsuarioApp;
import um.edu.ar.backend.infrastructure.persistence.repository.UsuarioAppRepository;
import um.edu.ar.backend.infrastructure.web.dto.LoginRequest;

import java.util.HashMap;
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

        log.info("[LOGIN] Request recibido: username='{}', password='{}'",
                request.username(), request.password());

        UsuarioApp usuario = usuarioRepo.findByUsername(request.username())
                .orElse(null);

        if (usuario == null) {
            log.warn("[LOGIN] Usuario no encontrado en DB: '{}'", request.username());
            r.put("resultado", false);
            r.put("descripcion", "Usuario no encontrado");
            return ResponseEntity.status(401).body(r);
        }

        log.info("[LOGIN] Usuario encontrado: username='{}', hash='{}'",
                usuario.getUsername(), usuario.getPassword());

        boolean ok = passwordEncoder.matches(request.password(), usuario.getPassword());
        log.info("[LOGIN] passwordEncoder.matches(...) = {}", ok);

        if (!ok) {
            r.put("resultado", false);
            r.put("descripcion", "Contraseña incorrecta");
            return ResponseEntity.status(401).body(r);
        }

        String sessionId = sesionService.crearSesion();

        r.put("resultado", true);
        r.put("descripcion", "Sesión iniciada correctamente");
        r.put("sessionId", sessionId);

        log.info("[LOGIN] Sesión creada: sessionId={}", sessionId);

        return ResponseEntity.ok(r);
    }
}

